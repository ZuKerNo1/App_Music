package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.musicapp.Adapter.SongAdapter;
import com.example.musicapp.Model.Favourite;
import com.example.musicapp.Model.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailSongActivity extends AppCompatActivity {
    ImageView btnPlay, nextMusic, previousMusic, repeat, shuffle, video,playList;
    SeekBar seekBar;
    public static MediaPlayer mediaPlayer;
    Runnable runnable;
    Animation animation;
    ImageView heart;
    Handler handler = new Handler();
    Boolean isRepeat = false, isShuffle = false;
    ArrayList<Integer> favList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static Activity ac;
    public static String key;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    boolean favourited;

    public static AppCompatActivity me;
    public static BottomNavigationView bottomNavigationView;

    CircleImageView image;
    TextView nameSong, singer, currentTime, totalTime;

    public static Song currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        me = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_song);
        ac = this;


        animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setSelectedItemId(R.id.play);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.play:
                        return true;
                    case R.id.library:
                        startActivity(new Intent(getApplicationContext(), LibraryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.hotList:
                        startActivity(new Intent(getApplicationContext(), HotListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

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
        playList = findViewById(R.id.playList);
        heart = findViewById(R.id.heart);
        video = findViewById(R.id.video);
        btnPlay = findViewById(R.id.play);
        seekBar = findViewById(R.id.seek_bar);


//        Lấy dữ liệu

        Bundle bundle = getIntent().getExtras();
        currentSong = (Song) bundle.get("object");
//        String resumeMusic = bundle.getString("resume");

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
                if(mediaPlayer.isPlaying()) {
                    btnPlay.setImageResource(R.drawable.ic_play_circle);
                    mediaPlayer.pause();
                }
                startActivity(intent);
            }
        });

//      Chạy nhạc khi vào trang detail
        prepareMediaPlayer(currentSong.getSong());

        playList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailSongActivity.this, PlayListActivity.class);
                ArrayList<Song> playList = SongAdapter.list;
                Bundle bundle = new Bundle();
                bundle.putSerializable("playList", playList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

//      Chuyển bài hat
        nextMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Song> listSong = SongAdapter.list;

                int position = 0;

                for (Song song : listSong) {
                    if (song.getId() == currentSong.getId())
                        position = listSong.indexOf(song);
                }
                position++;
                if (position < listSong.size()) {
                    currentSong = listSong.get(position);
                    mediaPlayer.reset();
                    Glide.with(DetailSongActivity.this).load(currentSong.getImage()).fitCenter().into(image);
                    nameSong.setText(currentSong.getNameSong());
                    singer.setText(currentSong.getSinger());
                    btnPlay.setImageResource(R.drawable.ic_play_circle);
                    prepareMediaPlayer(currentSong.getSong());
                } else {
                    mediaPlayer.reset();
                    prepareMediaPlayer(currentSong.getSong());
                }


            }
        });

        previousMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Song> listSong = SongAdapter.list;

                int position = 0;
                for (Song song : listSong) {
                    if (song.getId() == currentSong.getId())
                        position = listSong.indexOf(song);
                }
                position--;
                if (position >= 0) {
                    currentSong = listSong.get(position);
                    mediaPlayer.reset();

                    Glide.with(DetailSongActivity.this).load(currentSong.getImage()).fitCenter().into(image);
                    nameSong.setText(currentSong.getNameSong());
                    singer.setText(currentSong.getSinger());
                    btnPlay.setImageResource(R.drawable.ic_play_circle);
                    prepareMediaPlayer(currentSong.getSong());
                } else {
                    mediaPlayer.reset();
                    prepareMediaPlayer(currentSong.getSong());
                }

            }
        });

//       Auto chuyển bài hát khi hết
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                ArrayList<Song> listSong = SongAdapter.list;

                int position = 0;

                for (Song song : listSong) {
                    if (song.getId() == currentSong.getId())
                        position = listSong.indexOf(song);
                }
                position++;
                if (!isRepeat) {
                    if (isShuffle) {
                        Random random = new Random();
                        position = random.nextInt(listSong.size());
                        currentSong = listSong.get(position);
                        mediaPlayer.reset();
                        Glide.with(DetailSongActivity.this).load(currentSong.getImage()).fitCenter().into(image);
                        nameSong.setText(currentSong.getNameSong());
                        singer.setText(currentSong.getSinger());
                        btnPlay.setImageResource(R.drawable.ic_play_circle);
                        prepareMediaPlayer(currentSong.getSong());
                    } else {
                        if (position < listSong.size()) {
                            currentSong = listSong.get(position);
                            mediaPlayer.reset();
                            Glide.with(DetailSongActivity.this).load(currentSong.getImage()).fitCenter().into(image);
                            nameSong.setText(currentSong.getNameSong());
                            singer.setText(currentSong.getSinger());
                            btnPlay.setImageResource(R.drawable.ic_play_circle);
                            prepareMediaPlayer(currentSong.getSong());
                        } else {
                            mediaPlayer.reset();
                            prepareMediaPlayer(currentSong.getSong());
                        }
                    }
                } else if (isShuffle) {
                    mediaPlayer.reset();
                    prepareMediaPlayer(currentSong.getSong());
                } else {
                    mediaPlayer.reset();
                    prepareMediaPlayer(currentSong.getSong());
                }

            }
        });

//        Repeat nhạc
        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRepeat) {
                    repeat.setImageResource(R.drawable.ic_repeat);
                    isRepeat = true;
                } else {
                    repeat.setImageResource(R.drawable.ic_unrepeat);
                    isRepeat = false;
                }
            }
        });
//        Trộn nhạc
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShuffle) {
                    shuffle.setImageResource(R.drawable.ic_shuffle);
                    isShuffle = true;
                } else {
                    shuffle.setImageResource(R.drawable.ic_unshuffle);
                    isShuffle = false;
                }
            }
        });

//      Thêm yêu thích

        favList = new ArrayList<>();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("favourite");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Favourite favourite = dataSnapshot.getValue(Favourite.class);
                    if (favourite.getUid().equals(mAuth.getUid()) && favourite.getSongId()==currentSong.getId()) {
                        favourited=true;

                        setIconFavourited();

                        DetailSongActivity.key=dataSnapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setIconFavourited();


        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Favourite favourite = new Favourite(mAuth.getUid(), currentSong.getId());
                if (!favourited) {
                    heart.setImageResource(R.drawable.ic_heart_red);
                    FirebaseDatabase.getInstance().getReference("favourite").push().setValue(favourite);
                    Toast.makeText(DetailSongActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    favourited = true;

                } else {
                    heart.setImageResource(R.drawable.ic_heart_solid);

//                    Xóa data trên firebase
//                    
                    FirebaseDatabase.getInstance().getReference("favourite").child(DetailSongActivity.key) .removeValue();
                    Toast.makeText(DetailSongActivity.this, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    favourited = false;


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

//        Chuyển trạng thái icon
//        Chuyển trạng thái nhạc
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    handler.removeCallbacks(updater);
                    btnPlay.setImageResource(R.drawable.ic_play_circle);
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.ic_pause);
                    updateSeekbar();
                }
            }
        });


        //Bottom nav

    }

    //    Hàm chạy nhạc bằng url
    public void prepareMediaPlayer(String url) {
        mediaPlayer.reset();
//        mediaPlayer.reset();
        try {
            mediaPlayer.reset();
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

    public void updateSeekbar() {
        if (mediaPlayer.isPlaying()) {
            seekBar.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100));
            handler.postDelayed(updater, 1000);
        }
    }

    public String milliSecondToTimer(long milliSeconds) {
        String timerString = "";
        String secondString;

        int hours = (int) (milliSeconds / (1000 * 60 * 60));
        int minutes = (int) (milliSeconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliSeconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);

        if (hours > 0) {
            timerString = hours + ":";
        }

        if (seconds < 10) {
            secondString = "0" + seconds;
        } else {
            secondString = "" + seconds;
        }

        timerString = timerString + minutes + ":" + secondString;
        return timerString;
    }


    //check favourited
    public boolean checkFav(Song song, ArrayList<Integer> favListSong) {
        for (int i : favListSong) {
            if (i == song.getId())
                return true;
        }
        return false;
    }

    public void setIconFavourited() {
        if (favourited) {
            heart.setImageResource(R.drawable.ic_heart_red);
        } else
            heart.setImageResource(R.drawable.ic_heart_solid);

    }





}