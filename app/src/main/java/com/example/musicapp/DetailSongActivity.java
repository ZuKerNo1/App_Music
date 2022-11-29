package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

public class DetailSongActivity extends AppCompatActivity {
    ImageView btnPlay;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Runnable runnable;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_song);

        btnPlay = findViewById(R.id.play);
        seekBar = findViewById(R.id.seek_bar);

        mediaPlayer = new MediaPlayer();
        handler = new Handler();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b) {
                    mediaPlayer.seekTo(i);
                    seekBar.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlay.setImageResource(R.drawable.ic_pause);
                PlaySong();
            }
        });
    }
    public void PlaySong() {
        Uri uri = Uri.parse("https://www.bensound.com/bensound-music/bensound-summer.mp3");
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.reset();

        try {
            mediaPlayer.setDataSource(DetailSongActivity.this, uri);
        } catch (Exception e){
            e.printStackTrace();
        }

//        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mp.getDuration());
                mediaPlayer.start();
                updateSeekbar();
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                double ratio = i / 100.0;
                int bufferingLevel = (int)(mediaPlayer.getDuration() * ratio);
                seekBar.setSecondaryProgress(bufferingLevel);
            }
        });
    }

    public void updateSeekbar() {
        int curr = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(curr);

        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekbar();
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}