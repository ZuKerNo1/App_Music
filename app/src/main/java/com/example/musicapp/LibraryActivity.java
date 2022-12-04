package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.musicapp.Adapter.HotListAdapter;
import com.example.musicapp.Model.Favourite;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Service.DbSevice;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {
    public static AppCompatActivity myAc;

    DbSevice dbSevice = new DbSevice();
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    HotListAdapter hotListAdapter;
    ArrayList<Song> listSong;


    @Override
    protected void onPause() {
        myAc = this;
        myAc.finish();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        myAc = this;

        listSong = new ArrayList<>();
        //Ánh xạ
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hotListAdapter = new HotListAdapter(this, listSong);
        recyclerView.setAdapter(hotListAdapter);


//        Lấy list id bài hát yêu thích

        ArrayList<Integer> FavouriteList = new ArrayList<>();
        DatabaseReference databaseReference_f = FirebaseDatabase.getInstance().getReference("favourite");
        databaseReference_f.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Favourite favourite = dataSnapshot.getValue(Favourite.class);
                    if (favourite.getUid().equals(mAuth.getUid())) {
                        FavouriteList.add(favourite.getSongId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Lấy dữ liệu từ FireBase

        databaseReference = FirebaseDatabase.getInstance().getReference("song_gg");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Song song = dataSnapshot.getValue(Song.class);
                    if (FavouriteList.contains(song.getId())) {
                        listSong.add(song);

                    }
                }
                hotListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//
//        Bundle bundle = getIntent().getExtras();
//        String id = (String) bundle.get("Action_next");
//        firebaseDatabase.getInstance().getReference("Song").child(id);


//        Bottom Nav


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setSelectedItemId(R.id.library);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.library:
                        return true;
                    case R.id.search:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.play:
                        if(DetailSongActivity.me != null ){
                            myAc.finish();

                        } else{
                            Toast.makeText(LibraryActivity.this, "Không có bài hát nào đang chạy", Toast.LENGTH_SHORT).show();
                            return true;
                        }
//                        startActivity(new Intent(getApplicationContext(),DetailSongActivity.class));
//                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
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