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
import com.example.musicapp.Model.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HotListActivity extends AppCompatActivity {
    public static AppCompatActivity myAc;

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    HotListAdapter hotListAdapter;
    ArrayList<Song> list;

    @Override
    protected void onPause() {
        myAc = this;
        myAc.finish();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_list);
        myAc = this;

//Ánh xạ
        recyclerView = findViewById(R.id.recyclerView);

//        Lấy dữ liệu từ FireBase
        databaseReference = FirebaseDatabase.getInstance().getReference("song_gg");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        hotListAdapter = new HotListAdapter(this, list);
        recyclerView.setAdapter(hotListAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Song song = dataSnapshot.getValue(Song.class);
                    if(song.getType()!=null && song.getType().equals("HotList") ){
                        list.add(song);
                    }
                }
                hotListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        Bottom Nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setSelectedItemId(R.id.hotList);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.hotList:
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
                            Toast.makeText(HotListActivity.this, "Không có bài hát nào đang chạy", Toast.LENGTH_SHORT).show();
                            return true;
                        }
//                        startActivity(new Intent(getApplicationContext(),DetailSongActivity.class));
//                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.library:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(),LibraryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

}