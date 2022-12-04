package com.example.musicapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.DetailSongActivity;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.MyViewHolder>{
    Context context;
    public static ArrayList<Song> list;

    public SongAdapter(Context context, ArrayList<Song> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SongAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_library,parent,false);
        return new SongAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SongAdapter.MyViewHolder holder, int position) {
        Song song = list.get(position);

        holder.id.setText(String.valueOf(song.getId()));
        holder.nameSong.setText(song.getNameSong());
        holder.singer.setText(song.getSinger());
        Glide.with(context).load(song.getImage()).fitCenter();

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetailSong(song);
            }
        });

    }

    public void goToDetailSong(Song song) {
        Intent intent = new Intent(context, DetailSongActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object", song);

        intent. putExtras (bundle);

        context.startActivity (intent);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id, nameSong, singer;
        ConstraintLayout constraintLayout;

        @SuppressLint("ResourceType")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.idMusic);
            nameSong = itemView.findViewById(R.id.name_song);
            singer = itemView.findViewById(R.id.author);

            constraintLayout = itemView.findViewById(R.id.constraintLayout);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
