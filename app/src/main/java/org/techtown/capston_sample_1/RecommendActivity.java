package org.techtown.capston_sample_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RecommendActivity extends AppCompatActivity {

    Button buttonCancel_rec;
    Button btn_text1;
    Button btn_text2;
    Button btn_text3;
    Button btn_text4;
    TextView tv_finalText;
    ImageView iv_recommendImage;

    // 서버 통신
    String serverIp = "192.168.0.8";
    int serverPort = 2031;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        buttonCancel_rec = findViewById(R.id.buttonCancel_rec);
        btn_text1 = findViewById(R.id.btn_text1);
        btn_text2 = findViewById(R.id.btn_text2);
        btn_text3 = findViewById(R.id.btn_text3);
        btn_text4 = findViewById(R.id.btn_text4);
        tv_finalText = findViewById(R.id.textRecStyle);
        iv_recommendImage = findViewById(R.id.iv_recommendImage);

        Intent intent = getIntent();
        btn_text1.setText(intent.getStringExtra("text1"));
        btn_text2.setText(intent.getStringExtra("text2"));
        btn_text3.setText(intent.getStringExtra("text3"));
        btn_text4.setText(intent.getStringExtra("text4"));
        tv_finalText.setText(intent.getStringExtra("finalText"));

        btn_text1.setOnClickListener(v -> {
            loading();
            ImageRequester imageRequester = new ImageRequester(serverIp, serverPort, imageCallback);
            imageRequester.requestImage(btn_text1.getText().toString(), "Picasso", "100");
        });

        btn_text2.setOnClickListener(v -> {
            loading();
            ImageRequester imageRequester = new ImageRequester(serverIp, serverPort, imageCallback);
            imageRequester.requestImage(btn_text2.getText().toString(), "Picasso", "100");
        });

        btn_text3.setOnClickListener(v -> {
            loading();
            ImageRequester imageRequester = new ImageRequester(serverIp, serverPort, imageCallback);
            imageRequester.requestImage(btn_text3.getText().toString(), "Picasso", "100");
        });

        btn_text4.setOnClickListener(v -> {
            loading();
            ImageRequester imageRequester = new ImageRequester(serverIp, serverPort, imageCallback);
            imageRequester.requestImage(btn_text4.getText().toString(), "Picasso", "100");
        });

        buttonCancel_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            }
        });

    }

    private void loading() {
        Glide.with(this)
                .load(R.raw.loading)
                .into(iv_recommendImage);
    }

    ImageCallback imageCallback = new ImageCallback() {
        @Override
        public void onResult(ResultData resultData) {
            iv_recommendImage.setImageBitmap(resultData.getBitmap());
        }
    };
}