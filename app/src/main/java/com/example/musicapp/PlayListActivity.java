package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.musicapp.Adapter.SongAdapter;
import com.example.musicapp.Model.Song;

import java.util.ArrayList;

public class PlayListActivity extends AppCompatActivity {
    public static AppCompatActivity myAc;
    ArrayList<Song> PlayList;
    RecyclerView recyclerView;
    SongAdapter songAdapter;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        myAc = this;

        recyclerView = findViewById(R.id.recyclerViewHotList);
        back = findViewById(R.id.back);

        Bundle bundle = getIntent().getExtras();

        PlayList = (ArrayList<Song>) bundle.get("playList");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        songAdapter = new SongAdapter(this, PlayList);
        recyclerView.setAdapter(songAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAc.finish();
            }
        });
    }
}