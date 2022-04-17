package org.techtown.capston_sample_1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    boolean login = false;
    String id = "";
    String text = "";
    String style = "";
    String translatedInput; // 영어로 번역된 글자

    Button buttonRetry;

    ProgressDialog dialog;

    ImageView iv_result;

    // 서버 통신
    String serverIp = "192.168.0.1";
    int serverPort = 8000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        processIntent(intent);

        dialog = new ProgressDialog(ResultActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("이미지를 만드는 중입니다.");
        dialog.setCancelable(false);

        //dialog.show();

        //dialog.dismiss();

        iv_result = findViewById(R.id.iv_result);

        buttonRetry = findViewById(R.id.buttonRetry);
        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            }
        });

        // 서버로부터 결과 이미지 수신
        ImageRequester imageRequester = new ImageRequester(serverIp, serverPort, imageCallback);
        imageRequester.requestImage(translatedInput, style);

    }

    private void processIntent(Intent intent){

        if(intent != null){
            Bundle bundle = intent.getExtras();
            login = bundle.getBoolean("login");
            id = bundle.getString("id");
            text = bundle.getString("text");
            style = bundle.getString("style");
            translatedInput = bundle.getString("translatedInput");
        }
    }

    // 결과 이미지 콜백
    ImageCallback imageCallback = new ImageCallback() {
        @Override
        public void onResult(Bitmap resultBitmap) {
            iv_result.setImageBitmap(resultBitmap);
        }
    };

}
