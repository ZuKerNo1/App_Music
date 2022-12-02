package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Adapter.HotListAdapter;
import com.example.musicapp.Model.Song;
import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailSongActivity extends AppCompatActivity {
    ImageView btnPlay, nextMusic;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Runnable runnable;
    Animation animation;
    Handler handler = new Handler();
    HotListAdapter hotListAdapter;
    DatabaseReference databaseReference;

    CircleImageView image;
    TextView nameSong, singer, currentTime, totalTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_song);

        animation = AnimationUtils.loadAnimation(this, R.anim.rotate);

//        Ánh xạ
        image = findViewById(R.id.image_song);
        nameSong = findViewById(R.id.nameSong);
        singer = findViewById(R.id.author);
        currentTime = findViewById(R.id.currentTime);
        totalTime = findViewById(R.id.totalTime);
        nextMusic = findViewById(R.id.skip_next);

        btnPlay = findViewById(R.id.play);
        seekBar = findViewById(R.id.seek_bar);

//        Lấy dữ liệu

        Bundle bundle = getIntent().getExtras();
        Song hotList = (Song) bundle.get("object");

        Glide.with(this).load(hotList.getImage()).fitCenter().into(image);
        nameSong.setText(hotList.getNameSong());
        singer.setText(hotList.getSinger());

        mediaPlayer = new MediaPlayer();
        handler = new Handler();

//        Chạy nhạc khi vào trang detail
        prepareMediaPlayer(hotList.getSong());

//      Thêm yêu thích

        ImageView heart = findViewById(R.id.heart);




//        Tua nhạc
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                SeekBar seekBar = (SeekBar) view;
                int playPosition = (mediaPlayer.getDuration() / 100) * seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                currentTime.setText(milliSecondToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                seekBar.setSecondaryProgress(i);
            }
        });

//       Tự động chuyển bài
        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailSongActivity.this, HotListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Action_next", hotList.getId());
                intent. putExtras (bundle);
                startService (intent);
            }
        });



//        Chuyển trạng thái icon
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    btnPlay.setImageResource(R.drawable.ic_play_circle);
                    mediaPlayer.pause();
                }else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.ic_pause);
                    updateSeekbar();
                }
            }
        });
    }

    public void prepareMediaPlayer(String url){
        try {
            btnPlay.setImageResource(R.drawable.ic_pause);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            totalTime.setText(milliSecondToTimer(mediaPlayer.getDuration()));
            mediaPlayer.start();
            image.startAnimation(animation);
            updateSeekbar();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Runnable updater = new Runnable() {
        @Override
        public void run() {
            updateSeekbar();
            long currentDuration = mediaPlayer.getCurrentPosition();
            currentTime.setText(milliSecondToTimer(currentDuration));
        }
    };

    public void updateSeekbar(){
        if(mediaPlayer.isPlaying()){
            seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) *100));
            handler.postDelayed(updater, 1000);
        }
    }

    public String milliSecondToTimer(long milliSeconds){
        String timerString = "";
        String secondString;

        int hours = (int) (milliSeconds / (1000 * 60 * 60));
        int minutes = (int) (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if(hours > 0){
            timerString = hours + ":";
        }

        if(seconds < 10){
            secondString = "0" + seconds;
        }else{
            secondString = "" + seconds;
        }

        timerString = timerString + minutes + ":" + secondString;
        return timerString;
    }

//    public String AutoChangeSong(){
//        Bundle bundle = getIntent().getExtras();
//        Song hotList = (Song) bundle.get("object");
//
//    }

}