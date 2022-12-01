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

public class HotListAdapter extends RecyclerView.Adapter<HotListAdapter.MyViewHolder>{

    Context context;
    ArrayList<Song> list;

    public HotListAdapter(Context context, ArrayList<Song> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_library,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Song hotList = list.get(position);

        holder.id.setText(String.valueOf(hotList.getId()));
        holder.nameSong.setText(hotList.getNameSong());
        holder.singer.setText(hotList.getSinger());
        Glide.with(context).load(hotList.getImage()).fitCenter();

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDetailSong(hotList);
            }
        });

    }

    private void goToDetailSong(Song hotList) {
        Intent intent = new Intent(context, DetailSongActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object", hotList);

        intent. putExtras (bundle);

        context.startActivity (intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
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
}
