package com.example.musicapp.Model;

import java.io.Serializable;

<<<<<<< HEAD:app/src/main/java/com/example/musicapp/Model/HotList.java
public class HotList implements Serializable {
=======
public class Song implements Serializable {
>>>>>>> 1b1f3cb4ba5dea3a442104c55ce560848a1fda80:app/src/main/java/com/example/musicapp/Model/Song.java
    int id;
    String image, nameSong, singer, song;

    public Song() {
    }

<<<<<<< HEAD:app/src/main/java/com/example/musicapp/Model/HotList.java
    public HotList(int id, String image, String nameSong, String singer, String song) {
=======
    public Song(int id, String image, String nameSong, String singer, String song) {
>>>>>>> 1b1f3cb4ba5dea3a442104c55ce560848a1fda80:app/src/main/java/com/example/musicapp/Model/Song.java
        this.id = id;
        this.image = image;
        this.nameSong = nameSong;
        this.singer = singer;
        this.song = song;
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
