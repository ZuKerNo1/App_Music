package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int currentProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        runProg();
    }

    public void runProg() {
        progressBar = findViewById(R.id.progressBar);
        Intent intent = new Intent(LoadingActivity.this, HomeLoginActivity.class);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                currentProgress++;
                progressBar.setProgress(currentProgress);
                if(currentProgress == 100){
                    t.cancel();
                    startActivity(intent);
                }
            }
        };
        t.schedule(tt, 0, 100);
    }
}