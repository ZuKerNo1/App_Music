package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeLoginActivity extends AppCompatActivity {

    Button btnSignIn, btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_login);
        changeFrameSignIn();
        changeFrameSignUp();
    }

    public void changeFrameSignIn() {
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeLoginActivity.this,  SignInActivity.class);
                startActivity(intent);
            }
        });
    }
    public void changeFrameSignUp() {
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeLoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}