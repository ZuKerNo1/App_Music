package com.example.musicapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.MusicRVModel;
import com.example.musicapp.R;

import java.util.ArrayList;

public class MusicRVAdapter extends RecyclerView.Adapter<MusicRVAdapter.MusicRvHolder>{
    public ArrayList<MusicRVModel> musicRVModels;
    private  OnItemClickListner mListner;


    public interface  OnItemClickListner{
        void  onItemClick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner mListner){
        this.mListner = mListner;
    }

    public MusicRVAdapter(ArrayList<MusicRVModel> musicRVModels){
        this.musicRVModels = musicRVModels;
    }

    public class MusicRvHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        //ConstraintLayout constraintLayout;

        public MusicRvHolder(@NonNull View itemView, final OnItemClickListner mListner) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView1 = itemView.findViewById(R.id.name);
            textView2 = itemView.findViewById(R.id.casi);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListner!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListner.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MusicRVAdapter.MusicRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_rv_item,parent,false);
        MusicRvHolder dynamicRvHolder = new MusicRvHolder(view, mListner);
        return dynamicRvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicRVAdapter.MusicRvHolder holder, int position) {
        MusicRVModel currentItem = musicRVModels.get(position);
        holder.imageView.setImageResource(currentItem.getImage());
        holder.textView1.setText(currentItem.getName());
        holder.textView2.setText(currentItem.getCapbac());

    }

    @Override
    public int getItemCount() {
        return musicRVModels.size();
    }
}
