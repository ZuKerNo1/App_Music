package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.musicapp.Adapter.SearchAdapter;
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

public class SearchActivity extends AppCompatActivity {

    ArrayList<Song> listSongSearch;
    SearchAdapter songAdapter;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    androidx.appcompat.widget.SearchView searchView;
    AppCompatActivity myAc;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        myAc=this;

        recyclerView = findViewById(R.id.recyclerViewSearch);
        searchView = findViewById(R.id.textSearchView);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.search);

        //Lấy dữ liệu từ FireBase
        databaseReference = FirebaseDatabase.getInstance().getReference("song");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listSongSearch = new ArrayList<>();
        songAdapter = new SearchAdapter(this, listSongSearch);
        recyclerView.setAdapter(songAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Song song = dataSnapshot.getValue(Song.class);
                    listSongSearch.add(song);
                    songAdapter.notifyDataSetChanged();
                }
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
                    case R.id.library:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(),LibraryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        myAc.finish();
                        return true;
                    case R.id.play:
                        if(DetailSongActivity.me != null ){
                            DetailSongActivity.bottomNavigationView.setSelectedItemId(R.id.play);

                            myAc.finish();
                            overridePendingTransition(0, 0);
                        } else{
                            Toast.makeText(SearchActivity.this, "Không có bài hát nào đang chạy", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        overridePendingTransition(0, 0);
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

    //Tìm và đổ lại dữ liệu cho recyclerView
    private void filterSong(String query) {
        ArrayList<Song> filteredList = new ArrayList<>();

        if(listSongSearch.size() > 0)
        {
            for( Song songSearch : listSongSearch){
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