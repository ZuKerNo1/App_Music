package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public static AppCompatActivity myAc;

    @Override
    protected void onPause() {
        myAc = this;
        myAc.finish();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAc=this;

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.search:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.play:
                        if(DetailSongActivity.me != null){
                            DetailSongActivity.bottomNavigationView.setSelectedItemId(R.id.play);

                            myAc.finish();
                            overridePendingTransition(0, 0);
                        } else{
                            Toast.makeText(MainActivity.this, "Không có bài hát nào đang chạy", Toast.LENGTH_SHORT).show();
                        }
//                        startActivity(new Intent(getApplicationContext(),DetailSongActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.library:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(),LibraryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.hotList:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(),HotListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
}