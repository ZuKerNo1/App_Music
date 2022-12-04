package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.musicapp.Adapter.LofiAdapter;
import com.example.musicapp.Adapter.NewSongAdapter;
import com.example.musicapp.Adapter.POPAdapter;
import com.example.musicapp.Adapter.RAPAdapter;
import com.example.musicapp.Adapter.USUKAdapter;
import com.example.musicapp.Model.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static AppCompatActivity myAc;
    DatabaseReference databaseReference;
    ImageView logout;

    ArrayList<Song> list1, list2, list3, list4, list5;
    RecyclerView recyclerView1, recyclerView2, recyclerView3, recyclerView4, recyclerView5;

    @Override
    protected void onPause() {
        myAc = this;
        myAc.finish();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAc = this;

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.search:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.play:
                        if (DetailSongActivity.me != null) {
                            myAc.finish();

                        } else {
                            Toast.makeText(MainActivity.this, "Không có bài hát nào đang chạy", Toast.LENGTH_SHORT).show();
                        }
//                        startActivity(new Intent(getApplicationContext(),DetailSongActivity.class));
//                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.library:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(), LibraryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.hotList:
                        myAc.finish();
                        startActivity(new Intent(getApplicationContext(), HotListActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


//        Ánh xạ rv1
        recyclerView1 = findViewById(R.id.rv_1);

//        Lấy dữ liệu từ FireBase rv1
        databaseReference = FirebaseDatabase.getInstance().getReference("song");
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

        list1 = new ArrayList<>();
        NewSongAdapter hotListAdapter = new NewSongAdapter(this, list1);
        recyclerView1.setAdapter(hotListAdapter);


//        Lấy dữ liệu từ FireBase rv2
//        databaseReference = FirebaseDatabase.getInstance().getReference("song");
        recyclerView2 = findViewById(R.id.rv_2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

        list2 = new ArrayList<>();
        LofiAdapter lofiAdapter = new LofiAdapter(this, list2);
        recyclerView2.setAdapter(lofiAdapter);

//        Ánh xạ rv3
        recyclerView3 = findViewById(R.id.rv_3);
//        Lấy dữ liệu từ FireBase rv3
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));

        list3 = new ArrayList<>();
        RAPAdapter rapAdapter = new RAPAdapter(this, list3);
        recyclerView3.setAdapter(rapAdapter);


//       Ánh xạ rv4
        recyclerView4 = findViewById(R.id.rv_4);
//        Lấy dữ liệu từ FireBase rv4
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));

        list4 = new ArrayList<>();
        POPAdapter popAdapter = new POPAdapter(this, list4);
        recyclerView4.setAdapter(popAdapter);

        //       Ánh xạ rv5
        recyclerView5 = findViewById(R.id.rv_5);
//        Lấy dữ liệu từ FireBase rv5
        recyclerView5.setHasFixedSize(true);
        recyclerView5.setLayoutManager(new LinearLayoutManager(this));

        list5 = new ArrayList<>();
        USUKAdapter usukAdapter = new USUKAdapter(this, list5);
        recyclerView5.setAdapter(usukAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Song song = dataSnapshot.getValue(Song.class);
                    if (song.getType() != null) {

                        if (song.getType().equals("HotList")) {
                            list1.add(song);
                        } else if (song.getType().equals("Lofi")) {
                            list2.add(song);
                        } else if (song.getType().equals("RAP")) {
                            list3.add(song);
                        } else if (song.getType().equals("POP")) {
                            list4.add(song);
                        } else if (song.getType().equals("US-UK")) {
                            list5.add(song);
                        }
                    }


                    hotListAdapter.notifyDataSetChanged();
                    lofiAdapter.notifyDataSetChanged();
                    rapAdapter.notifyDataSetChanged();
                    rapAdapter.notifyDataSetChanged();
                    usukAdapter.notifyDataSetChanged();
                }
            }
                @Override
                public void onCancelled (@NonNull DatabaseError error){

                }
            });


        // Logout

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
        }
    }