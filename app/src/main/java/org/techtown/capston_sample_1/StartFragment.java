package org.techtown.capston_sample_1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class StartFragment extends Fragment {

    Button buttonLogin;
    Button buttonSingup;
    EditText textId;
    EditText textPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_start, container, false);

        buttonLogin = (Button) view.findViewById(R.id.buttonLogin);
        buttonSingup = (Button) view.findViewById(R.id.buttonSignup);
        textId = (EditText) view.findViewById(R.id.editTextId);
        textPassword = (EditText) view.findViewById(R.id.editTextPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((MainActivity)getActivity()).login == false) {
                    Toast.makeText(getContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).id = textId.getText().toString();
                    ((MainActivity) getActivity()).pwd = textPassword.getText().toString();
                    ((MainActivity) getActivity()).login = true;
                    buttonLogin.setText("LogOut");
                }else if(((MainActivity)getActivity()).login == true){
                    Toast.makeText(getContext(), "로그아웃 성공", Toast.LENGTH_SHORT).show();
                    textId.setText("");
                    textPassword.setText("");
                    ((MainActivity) getActivity()).id = "";
                    ((MainActivity) getActivity()).pwd = "";
                    ((MainActivity) getActivity()).login = false;
                    buttonLogin.setText("Log In");
                }
            }
        });

        buttonSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        return view;
    }
}