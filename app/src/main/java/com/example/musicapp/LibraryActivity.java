package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.musicapp.Adapter.SongAdapter;
import com.example.musicapp.Model.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {

    ArrayList<Song> listSongSearch;
    SongAdapter songAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        recyclerView = findViewById(R.id.recyclerViewLibrary);

        //Lấy dữ liệu từ FireBase
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listSongSearch = SongAdapter.list;
        songAdapter = new SongAdapter(this, listSongSearch);
        recyclerView.setAdapter(songAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setSelectedItemId(R.id.library);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.library:
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.hotList:
                        startActivity(new Intent(getApplicationContext(),HotListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search_btn, menu);

        MenuItem menuItem = findViewById(R.id.searchBtn);
        SearchView searchView = (SearchView) menuItem.getActionView();

        SearchSong(searchView);

        return super.onCreateOptionsMenu(menu);
    }

    private void SearchSong(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSong(newText.toLowerCase());
                return true;
            }
        });
    }

    private void filterSong(String query) {
        ArrayList<Song> filteredList = new ArrayList<>();

        if(listSongSearch.size() > 0)
        {
            for( Song songsearch : listSongSearch){
                if(songsearch.getNameSong().toLowerCase().contains(query)){
                    filteredList.add(songsearch);
                }
                if (songAdapter != null){
                    songAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}