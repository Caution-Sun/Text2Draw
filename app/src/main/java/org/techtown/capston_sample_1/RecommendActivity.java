package org.techtown.capston_sample_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecommendActivity extends AppCompatActivity {

    Button buttonCancel_rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        buttonCancel_rec = findViewById(R.id.buttonCancel_rec);

        buttonCancel_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}