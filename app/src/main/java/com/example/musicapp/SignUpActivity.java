package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Model.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth auth;
    private EditText editTextEmail, editTextpassword, editTextconfirm;
    private Button signUp;
    private TextView signin_btn;
    private ImageView imgBack;
    String email, password, name;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextpassword = findViewById(R.id.editTextPassword);
        editTextconfirm = findViewById(R.id.editTextPasswordConfirm);
        email = editTextEmail.toString();
        password = editTextpassword.toString();
        signUp = findViewById(R.id.btnSignUp);
        signin_btn = findViewById(R.id.btnSignIn);
        imgBack = findViewById(R.id.imgBack);
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, HomeLoginActivity.class);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextpassword.getText().toString().trim();
                String confirmPass = editTextconfirm.getText().toString().trim();
                if (email.isEmpty()) {
                    editTextEmail.setError("Email is required");
                }
                if (password.isEmpty()) {
                    editTextpassword.setError("Password is required");
                }
                if(confirmPass.isEmpty()){
                    editTextconfirm.setError("ConfirmPass is required");
                }else if(password.equals(confirmPass)){
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Sign up successfuly", Toast.LENGTH_SHORT).show();
                                SignUp();
                            }
                            else {
                                Toast.makeText(SignUpActivity.this, "Sign up failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    editTextconfirm.setError("ConfirmPass is incorrect");
                }

            }
        });

    }

    public void SignUp() {
        FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email.trim(), password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        firebaseUser.updateProfile(userProfileChangeRequest);
                        userModel userModel = new userModel(FirebaseAuth.getInstance().getUid(), name, email, password);
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel);
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}