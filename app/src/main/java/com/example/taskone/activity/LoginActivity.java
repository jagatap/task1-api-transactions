package com.example.taskone.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskone.utils.GlobalData;
import com.example.taskone.R;
import com.example.taskone.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    TextView textViewLoginResult;
    AppCompatEditText editTextUsername;
    AppCompatEditText editTextPassword;
    Button buttonLogin;
    ProgressBar progressBar;

    LoginViewModel mLoginViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewLoginResult = findViewById(R.id.textViewLoginResult);
        buttonLogin = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progressBar);
        if (GlobalData.getSharePreferenceString(getApplicationContext(), "token", "") != null && !GlobalData.getSharePreferenceString(getApplicationContext(), "token", "").isEmpty()) {
            GlobalData.setBiometricPrompt(this, HomeActivity.class);
        }


        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        mLoginViewModel.getProgress().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                progressBar.setVisibility(visibility);
            }
        });
        mLoginViewModel.getLoginResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textViewLoginResult.setText(s);
                if (s.equals("Login Success")) {

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginViewModel.login(getApplicationContext(), editTextUsername.getText().toString(), editTextPassword.getText().toString());


            }
        });
    }
}



