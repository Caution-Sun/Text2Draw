package org.techtown.capston_sample_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class GoogleSignupActivity extends AppCompatActivity {

    String id;
    String agebuffer;
    int age = 0;
    int sex = 0;
    int artist = 0;

    EditText editTextSignupId;
    EditText editTextSignupAge;

    Button buttonCancel;
    Button buttonSign;
    Spinner spinnerSex;
    Spinner spinnerArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonSign = findViewById(R.id.buttonSignG);

        editTextSignupId = findViewById(R.id.editTextSignupIdG);
        editTextSignupAge = findViewById(R.id.editTextSignupAgeG);

        spinnerSex = findViewById(R.id.spinnerSexG);
        ArrayAdapter<CharSequence> spinnerSex_adapter = ArrayAdapter.createFromResource(this, R.array.resourcesSex, android.R.layout.simple_spinner_item);
        spinnerSex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(spinnerSex_adapter);


        spinnerArtist = findViewById(R.id.spinnerArtistG);
        ArrayAdapter<CharSequence> spinnerArtist_adapter = ArrayAdapter.createFromResource(this, R.array.resourcesArtist, android.R.layout.simple_spinner_item);
        spinnerArtist_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArtist.setAdapter(spinnerArtist_adapter);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);

                finish();
            }
        });

        spinnerSex.setSelection(0);

        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sex = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerArtist.setSelection(0);

        spinnerArtist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                artist = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = editTextSignupId.getText().toString();
                agebuffer = editTextSignupAge.getText().toString();

                if(id.length() == 0){
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(agebuffer.length() == 0){
                    Toast.makeText(getApplicationContext(),"나이를 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else{

                    age = Integer.parseInt(agebuffer);
                    Toast.makeText(getApplicationContext(),"회원가입에 성공했습니다",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });
    }
}