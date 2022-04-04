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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {

    String id;
    String pwd;
    String pwd2;
    int sex = 0;
    int artist = 0;

    EditText editTextSignupId;
    EditText editTextSignupPwd;
    EditText editTextSignupPwd2;

    Button buttonCancel;
    Button buttonSign;
    Spinner spinnerSex;
    Spinner spinnerArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonSign = findViewById(R.id.buttonSign);

        editTextSignupId = findViewById(R.id.editTextSignupId);
        editTextSignupPwd = findViewById(R.id.editTextSignupPwd);
        editTextSignupPwd2 = findViewById(R.id.editTextSignupPwd2);

        spinnerSex = findViewById(R.id.spinnerSex);
        ArrayAdapter<CharSequence> spinnerSex_adapter = ArrayAdapter.createFromResource(this, R.array.resourcesSex, android.R.layout.simple_spinner_item);
        spinnerSex_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(spinnerSex_adapter);


        spinnerArtist = findViewById(R.id.spinnerArtist);
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
                pwd = editTextSignupPwd.getText().toString();
                pwd2 = editTextSignupPwd2.getText().toString();

                if(id.length() == 0){
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(pwd.length() == 0){
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(pwd2.length() == 0){
                    Toast.makeText(getApplicationContext(),"비밀번호를 재입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(pwd.equals(pwd2) == false){
                    Toast.makeText(getApplicationContext(),"재입력된 비밀번호가 잘못됬습니다",Toast.LENGTH_SHORT).show();
                }
                else{

                    Toast.makeText(getApplicationContext(),"회원가입에 성공했습니다",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });

    }
}