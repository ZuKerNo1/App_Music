package com.example.musicapp.Model;

import java.io.Serializable;

public class HotList implements Serializable {
    String id, image, nameSong, singer, song;

    public HotList() {
    }

    public HotList(String id, String image, String nameSong, String singer, String song) {
        this.id = id;
        this.image = image;
        this.nameSong = nameSong;
        this.singer = singer;
        this.song = song;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }
}
