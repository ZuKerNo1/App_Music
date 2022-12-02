package com.example.musicapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.Model.MusicRVModel;
import com.example.musicapp.Model.StaticRvModel;
import com.example.musicapp.R;
import com.example.musicapp.UpdateRecyclerView;

import java.util.ArrayList;

public class StaticRvAdapter extends RecyclerView.Adapter<StaticRvAdapter.StaticRVViewHolder>{

    private ArrayList<StaticRvModel> items;
    int row_index = -1;
    UpdateRecyclerView updateRecyclerView;
    Activity activity;
    boolean check = true;
    boolean selected = true;

    public StaticRvAdapter(ArrayList<StaticRvModel> items, Activity activity, UpdateRecyclerView updateRecyclerView) {
        this.items = items;
        this.activity = activity;
        this.updateRecyclerView = updateRecyclerView;
    }

    @NonNull
    @Override
    public StaticRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Lien ket xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.static_rv_item,parent,false);
        StaticRVViewHolder staticRVViewHolder = new StaticRVViewHolder(view);
        return  staticRVViewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull StaticRVViewHolder holder, int position) {
        StaticRvModel currentItem = items.get(position);
        holder.imageView.setImageResource(currentItem.getImage());
        holder.textView.setText(currentItem.getText());

        if (check){

            ArrayList<MusicRVModel> items =  new ArrayList<MusicRVModel>();
            items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa, 0));
            items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
            items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
            items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
            items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
            items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
            items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
            items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
            items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));

            updateRecyclerView.callback(position, items);

            check   = false;
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();

                if (position==0){
                    ArrayList<MusicRVModel> items =  new ArrayList<MusicRVModel>();
                    items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa, 0));
                    items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
                    items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
                    items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
                    items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
                    items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
                    items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
                    items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));
                    items.add(new MusicRVModel("Nguyễn Văn Trung","Đại tá",R.drawable.lisa,0));

                    updateRecyclerView.callback(position, items);

                }

                else if (position==1){
                    ArrayList<MusicRVModel> items =  new ArrayList<MusicRVModel>();
                    items.add(new MusicRVModel("Trump Nguyen","Đại tá",R.drawable.jisoo,1));
                    items.add(new MusicRVModel("Trump Nguyen","Đại tá",R.drawable.jisoo,1));
                    items.add(new MusicRVModel("Trump Nguyen","Đại tá",R.drawable.jisoo,1));
                    items.add(new MusicRVModel("Trump Nguyen","Đại tá",R.drawable.jisoo,1));
                    items.add(new MusicRVModel("Trump Nguyen","Đại tá",R.drawable.jisoo,1));
                    items.add(new MusicRVModel("Trump Nguyen","Đại tá",R.drawable.jisoo,1));
                    items.add(new MusicRVModel("Trump Nguyen","Đại tá",R.drawable.jisoo,1));
                    items.add(new MusicRVModel("Trump Nguyen","Đại tá",R.drawable.jisoo,1));
                    items.add(new MusicRVModel("Trump Nguyen","Đại tá",R.drawable.jisoo,1));

                    updateRecyclerView.callback(position, items);
                }

                else if (position==2){
                    ArrayList<MusicRVModel> items =  new ArrayList<MusicRVModel>();
                    items.add(new MusicRVModel("Wolf Niken","Đại tá",R.drawable.lisa,2));
                    items.add(new MusicRVModel("Wolf Niken","Đại tá",R.drawable.lisa,2));
                    items.add(new MusicRVModel("Wolf Niken","Đại tá",R.drawable.lisa,2));
                    items.add(new MusicRVModel("Wolf Niken","Đại tá",R.drawable.lisa,2));
                    items.add(new MusicRVModel("Wolf Niken","Đại tá",R.drawable.lisa,2));
                    items.add(new MusicRVModel("Wolf Niken","Đại tá",R.drawable.lisa,2));
                    items.add(new MusicRVModel("Wolf Niken","Đại tá",R.drawable.lisa,2));
                    items.add(new MusicRVModel("Wolf Niken","Đại tá",R.drawable.lisa,2));
                    items.add(new MusicRVModel("Wolf Niken","Đại tá",R.drawable.lisa,2));

                    updateRecyclerView.callback(position, items);
                }

                else if (position==3){
                    ArrayList<MusicRVModel> items =  new ArrayList<MusicRVModel>();
                    items.add(new MusicRVModel("Lee Min Ho","Đại tá",R.drawable.jisoo,3));
                    items.add(new MusicRVModel("Lee Min Ho","Đại tá",R.drawable.jisoo,3));
                    items.add(new MusicRVModel("Lee Min Ho","Đại tá",R.drawable.jisoo,3));
                    items.add(new MusicRVModel("Lee Min Ho","Đại tá",R.drawable.jisoo,3));
                    items.add(new MusicRVModel("Lee Min Ho","Đại tá",R.drawable.jisoo,3));
                    items.add(new MusicRVModel("Lee Min Ho","Đại tá",R.drawable.jisoo,3));
                    items.add(new MusicRVModel("Lee Min Ho","Đại tá",R.drawable.jisoo,3));
                    items.add(new MusicRVModel("Lee Min Ho","Đại tá",R.drawable.jisoo,3));
                    items.add(new MusicRVModel("Lee Min Ho","Đại tá",R.drawable.jisoo,3));

                    updateRecyclerView.callback(position, items);
                }
            }
        });
//
        if (selected){
            if (position==0)
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_selected_bg);
            selected = false;
        }
        else {
            if (row_index == position){
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_selected_bg);
            }
            else{
                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);
            }
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class StaticRVViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;
        LinearLayout linearLayout;

        public StaticRVViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
