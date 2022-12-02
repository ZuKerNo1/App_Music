package com.example.musicapp.Model;

public class Favourite {
    private String uid;
    private int songId;

    public Favourite() {
    }

    public Favourite(String uid, int  songId) {
        this.uid = uid;
        this.songId = songId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }
}
