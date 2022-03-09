package org.techtown.capston_sample_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    boolean login = false;
    String id = "";
    String text = "";
    String style = "";

    Button buttonRetry;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        processIntent(intent);

        buttonRetry = findViewById(R.id.buttonRetry);
        buttonRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            }
        });



    }

    private void processIntent(Intent intent){

        if(intent != null){
            Bundle bundle = intent.getExtras();
            login = bundle.getBoolean("login");
            id = bundle.getString("id");
            text = bundle.getString("text");
            style = bundle.getString("style");
        }
    }

}
