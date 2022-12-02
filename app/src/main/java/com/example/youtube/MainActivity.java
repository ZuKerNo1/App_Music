package com.example.youtube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController;

public class MainActivity extends AppCompatActivity {
    String nameSong_intent, singer_intent, videoId;
    TextView nameSong, singer;
    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        nameSong = findViewById(R.id.nameSong);
        singer = findViewById(R.id.singer);
        getData();
        setData();
        getLifecycle().addObserver(youTubePlayerView);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }
    private void getData() {
        if(getIntent().hasExtra("nameSong") && getIntent().hasExtra("singer") && getIntent().hasExtra("videoId")) {
            nameSong_intent = getIntent().getStringExtra("nameSong");
            singer_intent = getIntent().getStringExtra("singer");
            videoId = getIntent().getStringExtra("videoId");
        }
    }

    private void setData() {
        nameSong.setText(nameSong_intent);
        singer.setText(singer_intent);
    }
}