package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private EditText emailEdit, passEdit;
    private Button loginBtn;
    private TextView registerBtn;
    private FirebaseAuth mAuth;
    private ImageView imgBack;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        emailEdit = findViewById(R.id.editTextEmail);
        passEdit = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.btnSignIn);
        registerBtn = findViewById(R.id.btnSignUp);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, HomeLoginActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        //Remember Me

    }

    private void register() {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void login() {
        String email, pass;
        email = emailEdit.getText().toString();
        pass = passEdit.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,"Enter your Email!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass)){
            Toast.makeText(this,"Enter your Password!", Toast.LENGTH_LONG).show();
            return;
        }

//        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }else{
//                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email.trim(), pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String uid = mAuth.getUid();
                        Toast.makeText(SignInActivity.this,"Sign in Successfull !",Toast.LENGTH_LONG ).show();
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Sign in Failed !", Toast.LENGTH_SHORT).show();
                    }

                    }
                });

    }
}