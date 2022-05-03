package org.techtown.capston_sample_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ImageRequester {
    final String serverIp;
    final int serverPort;

    private Socket clientSocket;
    private PrintWriter pw;

    private Disposable disposable;
    private ImageCallback imageCallback;

    private Bitmap resultBitmap;

    public ImageRequester(String serverIp, int serverPort, ImageCallback imageCallback) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.imageCallback = imageCallback;
    }

    public void requestImage(String translatedInput, String styleInput, String quality) {
        execute(translatedInput, styleInput, quality);
    }

    private void execute(String translatedInput, String styleInput, String quality) {
        disposable = Observable.fromCallable(() -> {
            try {
                clientSocket = new Socket();
                clientSocket.connect(new InetSocketAddress(serverIp, serverPort), 5 * 1000);

                // 데이터 전송
                pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
                pw.write(translatedInput + "|" + styleInput + "|" + quality);
                pw.flush();

                // 데이터 수신
                resultBitmap = BitmapFactory.decodeStream(clientSocket.getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(pw != null)
                        pw.close();
                    if(clientSocket != null)
                        clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return resultBitmap;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Throwable {
                imageCallback.onResult(bitmap);
            }
        });
    }

}
