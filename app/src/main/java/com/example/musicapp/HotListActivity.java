package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.musicapp.Adapter.SongAdapter;
import com.example.musicapp.Model.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class HotListActivity extends AppCompatActivity {
    public static AppCompatActivity myAc;

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    SongAdapter songAdapter;
    ArrayList<Song> list;
    androidx.appcompat.widget.SearchView searchView;

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
        recyclerView = findViewById(R.id.recyclerViewHotList);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.hotList);
        searchView = findViewById(R.id.textSearchViewHostList);

//        Lấy dữ liệu từ FireBase
        databaseReference = FirebaseDatabase.getInstance().getReference("song");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        songAdapter = new SongAdapter(this, list);
        recyclerView.setAdapter(songAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Song song = dataSnapshot.getValue(Song.class);
                    if(song.getType()!=null && song.getType().equals("HotList") ){
                        list.add(song);
                    }
                }

                songAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Nhận ký tự tìm
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSong(converToString(newText.toLowerCase().trim()));
                return true;
            }
        });

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
                            DetailSongActivity.bottomNavigationView.setSelectedItemId(R.id.play);
                            myAc.finish();
                            overridePendingTransition(0, 0);

                        } else{
                            Toast.makeText(HotListActivity.this, "Không có bài hát nào đang chạy", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        overridePendingTransition(0, 0);
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

    //Tìm và đổ lại dữ liệu cho recyclerView
    private void filterSong(String query) {
        ArrayList<Song> filteredList = new ArrayList<>();

        if(list.size() > 0)
        {
            for( Song songSearch : list){
                if(converToString(songSearch.getNameSong().toLowerCase()).contains(query) || converToString(songSearch.getSinger().toLowerCase()).contains(query)){
                    filteredList.add(songSearch);
                }
            }
            if(songAdapter != null){
                songAdapter.filterSongs(filteredList);
            }
        }
    }

    //Chuyển từ tiếng việt sang không dấu
    public String converToString(String value){
        try {
            String temp = Normalizer.normalize(value, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            temp = pattern.matcher(temp).replaceAll("");
            return temp.replaceAll("đ", "d");

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}