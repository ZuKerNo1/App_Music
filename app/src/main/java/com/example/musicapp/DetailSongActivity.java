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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailSongActivity extends AppCompatActivity {
    ImageView btnPlay, nextMusic, previousMusic, repeat, shuffle, video;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Runnable runnable;
    Animation animation;
    Handler handler = new Handler();
    Boolean isRepeat = false, isShuffle = false;


    CircleImageView image;
    TextView nameSong, singer, currentTime, totalTime;

    public static Song currentSong;


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
        previousMusic = findViewById(R.id.skip_previous);
        repeat = findViewById(R.id.repeat);
        shuffle = findViewById(R.id.shuffle);
        video = findViewById(R.id.video);
        btnPlay = findViewById(R.id.play);
        seekBar = findViewById(R.id.seek_bar);

//        Lấy dữ liệu

        Bundle bundle = getIntent().getExtras();
         currentSong = (Song) bundle.get("object");

        Glide.with(this).load(currentSong.getImage()).fitCenter().into(image);
        nameSong.setText(currentSong.getNameSong());
        singer.setText(currentSong.getSinger());


        mediaPlayer = new MediaPlayer();
        handler = new Handler();
//        Xem MV

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailSongActivity.this, MusicVideoActivity.class);
                intent.putExtra("nameSong", currentSong.getNameSong());
                intent.putExtra("singer", currentSong.getSinger());
                if(currentSong.getVideoId() != null) {
                    intent.putExtra("videoId", currentSong.getVideoId());
                }
            }
        });

//      Chạy nhạc khi vào trang detail
        prepareMediaPlayer(currentSong.getSong());

//      Chuyển bài hat
        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                ArrayList<Song> listSong = (ArrayList<Song>) bundle.get("listSong");

                int position = 0;

                for (Song song : listSong) {
                    if (song.getId() == currentSong.getId())
                        position = listSong.indexOf(song);
                }
                position++;
                if(position < listSong.size()){
                    currentSong = listSong.get(position);
                    mediaPlayer.reset();
                    Glide.with(DetailSongActivity.this).load(currentSong.getImage()).fitCenter().into(image);
                    nameSong.setText(currentSong.getNameSong());
                    singer.setText(currentSong.getSinger());
                    btnPlay.setImageResource(R.drawable.ic_play_circle);
                    prepareMediaPlayer(currentSong.getSong());
                }else{
                    mediaPlayer.reset();
                    prepareMediaPlayer(currentSong.getSong());
                }


            }
        });

        previousMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                ArrayList<Song> listSong = (ArrayList<Song>) bundle.get("listSong");

                int position = 0;
                for (Song song : listSong) {
                    if (song.getId() == currentSong.getId() )
                        position = listSong.indexOf(song);
                }
                position--;
                if(position >= 0){
                    currentSong = listSong.get(position);
                    mediaPlayer.reset();

                    Glide.with(DetailSongActivity.this).load(currentSong.getImage()).fitCenter().into(image);
                    nameSong.setText(currentSong.getNameSong());
                    singer.setText(currentSong.getSinger());
                    btnPlay.setImageResource(R.drawable.ic_play_circle);
                    prepareMediaPlayer(currentSong.getSong());
                }
                else{
                    mediaPlayer.reset();
                    prepareMediaPlayer(currentSong.getSong());
                }

            }
        });

//       Auto chuyển bài hát khi hết
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Bundle bundle = getIntent().getExtras();
                ArrayList<Song> listSong = (ArrayList<Song>) bundle.get("listSong");

                int position = 0;

                for (Song song : listSong) {
                    if (song.getId() == currentSong.getId())
                        position = listSong.indexOf(song);
                }
                position++;
                if(position < listSong.size()){
                    currentSong = listSong.get(position);
                    mediaPlayer.reset();
                    Glide.with(DetailSongActivity.this).load(currentSong.getImage()).fitCenter().into(image);
                    nameSong.setText(currentSong.getNameSong());
                    singer.setText(currentSong.getSinger());
                    btnPlay.setImageResource(R.drawable.ic_play_circle);
                    prepareMediaPlayer(currentSong.getSong());
                }else{
                    mediaPlayer.reset();
                    prepareMediaPlayer(currentSong.getSong());
                }
            }
        });

//        Repeat nhạc
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isRepeat){
                    if(isShuffle){
                        isShuffle = false;
                        repeat.setImageResource(R.drawable.ic_repeat);
                        shuffle.setImageResource(R.drawable.ic_unshuffle);
                    }else{
                        shuffle.setImageResource(R.drawable.ic_unshuffle);
                    }
                    isRepeat = true;
                }else{
                    repeat.setImageResource(R.drawable.ic_repeat);
                    isRepeat = false;
                }
            }
        });
//        Trộn nhạc
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShuffle){
                    if (isRepeat){
                        isRepeat = false;
                        shuffle.setImageResource(R.drawable.ic_unshuffle);
                        repeat.setImageResource(R.drawable.ic_repeat);
                    }else {
                        shuffle.setImageResource(R.drawable.ic_unshuffle);
                    }
                    isShuffle = true;
                }else {
                    shuffle.setImageResource(R.drawable.ic_shuffle);
                    isShuffle = false;
                }
            }
        });

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


//        Chuyển trạng thái nhạc
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

//    Hàm chạy nhạc bằng url
    public void prepareMediaPlayer(String url){
//        mediaPlayer.reset();
        try {
            btnPlay.setImageResource(R.drawable.ic_pause);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
            totalTime.setText(milliSecondToTimer(mediaPlayer.getDuration()));
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


}