package com.example.musicapp.Service;

import androidx.annotation.NonNull;

import com.example.musicapp.Adapter.SongAdapter;
import com.example.musicapp.Model.Favourite;
import com.example.musicapp.Model.Song;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DbSevice {


    ArrayList<Song> listFavouriteSong;
    ArrayList<Song> listSong;


    FirebaseDatabase firebaseDatabase;
    SongAdapter songAdapter;
    ArrayList<Song> list;
    ArrayList<Song> MonoList;


    //get list song
    public  ArrayList<Song> getListSong (){
        listSong = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("song");
         databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Song song = dataSnapshot.getValue(Song.class);
                    listSong.add(song);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listSong;
    }

    //get user  favourite list
    public  ArrayList<Integer> getFavourite (String uid){
        ArrayList<Integer> list = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("favourite");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Favourite favourite = dataSnapshot.getValue(Favourite.class);
                    if(favourite.getUid().equals(uid)){
                        list.add(favourite.getSongId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }


    //get list favourite song by uid
    public  ArrayList<Song> getListFavouriteSongByUid(String uid){
        ArrayList<Integer> favourites = getFavourite(uid);
        ArrayList<Song> songs = getListSong();
        ArrayList<Song> ListFavouriteSong = new ArrayList<>();
        for (Song song : songs) {
            if(favourites.contains(song.getId()) ){
                ListFavouriteSong.add(song);
            }
        }
        return ListFavouriteSong;
    }

}
