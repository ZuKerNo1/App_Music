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

import de.hdodenhof.circleimageview.CircleImageView;

public class RAPAdapter extends RecyclerView.Adapter<RAPAdapter.MyViewHolder>{

    public static Context context;
    public static ArrayList<Song> list;

    public RAPAdapter(Context context, ArrayList<Song> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RAPAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_library,parent,false);
        return new RAPAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RAPAdapter.MyViewHolder holder, int position) {
        Song hotList = list.get(position);

        holder.nameSong.setText(hotList.getNameSong());
        holder.singer.setText(hotList.getSinger());
        Glide.with(context).load(hotList.getImage()).fitCenter().into(holder.image);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DetailSongActivity.me != null && DetailSongActivity.mediaPlayer.isPlaying()){
                    DetailSongActivity.me.finish();
                    DetailSongActivity.mediaPlayer.reset();
                    goToDetailSong(hotList);
                } else{
                    goToDetailSong(hotList);
                }


            }
        });

    }

    public static void goToDetailSong(Song songClick) {
        Intent intent = new Intent(context, DetailSongActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object", songClick);
        intent. putExtras (bundle);
        context.startActivity (intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView image;
        TextView nameSong, singer;
        ConstraintLayout constraintLayout;

        @SuppressLint("ResourceType")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageMusic);
            nameSong = itemView.findViewById(R.id.name_song);
            singer = itemView.findViewById(R.id.author);

            constraintLayout = itemView.findViewById(R.id.constraintLayout);

        }
    }
}
