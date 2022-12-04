package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicapp.Model.Song;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class MusicVideoActivity extends AppCompatActivity {
    public static AppCompatActivity myAc;

    String videoId;
    TextView nameSong, singer;
    ImageView back;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_video);

        myAc = this;

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        nameSong = findViewById(R.id.nameSong);
        singer = findViewById(R.id.singer);
        back = findViewById(R.id.BtnBack);

        nameSong.setText(getIntent().getStringExtra("nameSong"));
        singer.setText(getIntent().getStringExtra("singer"));
        videoId = getIntent().getStringExtra("videoId");

        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAc.finish();
            }
        });
    }
}