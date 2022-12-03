package com.example.musicapp.Model;

import java.io.Serializable;



public class Song implements Serializable {
    int id;
    String image, nameSong, singer, song, type;

    public Song() {
    }

    public Song(int id, String image, String nameSong, String singer, String song, String type) {
        this.id = id;
        this.image = image;
        this.nameSong = nameSong;
        this.singer = singer;
        this.song = song;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
