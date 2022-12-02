package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.musicapp.Adapter.MusicRVAdapter;
import com.example.musicapp.Adapter.StaticRvAdapter;
import com.example.musicapp.Model.MusicRVModel;
import com.example.musicapp.Model.StaticRvModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UpdateRecyclerView{

    private RecyclerView recyclerView, recyclerView2;
    private StaticRvAdapter staticRvAdapter;

    ArrayList<MusicRVModel> items = new ArrayList<>();
    MusicRVAdapter dynamicRVAdapter;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.library:
                        startActivity(new Intent(getApplicationContext(),LibraryActivity.class));
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

        final ArrayList<StaticRvModel> item = new ArrayList<>();
        item.add(new StaticRvModel(R.drawable.jisoo, "Việt Nam"));
        item.add(new StaticRvModel(R.drawable.lisa, "Hàn Quốc"));
        item.add(new StaticRvModel(R.drawable.tatdinhdinh, "Mỹ"));
        item.add(new StaticRvModel(R.drawable.taylor, "Anh"));

        recyclerView = findViewById(R.id.rv_1);
        staticRvAdapter = new StaticRvAdapter(item, MainActivity.this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(staticRvAdapter);


        items = new ArrayList<>();

        recyclerView2 = findViewById(R.id.rv_2);
        dynamicRVAdapter = new MusicRVAdapter(items);
        recyclerView2.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.setAdapter(dynamicRVAdapter);
    }
    @Override
    public void callback(int position, ArrayList<MusicRVModel> items) {

        dynamicRVAdapter = new MusicRVAdapter(items);
        dynamicRVAdapter.notifyDataSetChanged();
        recyclerView2.setAdapter(dynamicRVAdapter);
    }
}