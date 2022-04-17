package org.techtown.capston_sample_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ImageRequester {
    final String serverIp;
    final int serverPort;

    private Socket clientSocket;
    private PrintWriter pw;

    private ImageAsyncTask imageAsyncTask;
    private ImageCallback imageCallback;

    private Bitmap resultBitmap;

    public ImageRequester(String serverIp, int serverPort, ImageCallback imageCallback) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.imageCallback = imageCallback;
    }

    public void requestImage(String translatedInput, String styleInput) {
        imageAsyncTask = new ImageAsyncTask();
        imageAsyncTask.execute(translatedInput, styleInput);
    }

    private class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                clientSocket = new Socket();
                clientSocket.connect(new InetSocketAddress(serverIp, serverPort), 5 * 1000);

                // 데이터 전송
                pw = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
                pw.write(strings[0]);
                pw.write(strings[1]);
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
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageCallback.onResult(bitmap);
        }
    }

}
