package com.example.musicapp;

import com.example.musicapp.Model.MusicRVModel;

import java.util.ArrayList;

public interface UpdateRecyclerView {
    public void callback(int position, ArrayList<MusicRVModel> items);
}
