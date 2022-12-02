package com.example.musicapp.Model;

public class MusicRVModel {
    String name;
    private int image;
    private String casi;
    int pos;


    public MusicRVModel(String name, String casi , int image, int pos){

        this.name = name;
        this.image = image;
        this.casi = casi;
        this.pos = pos;
    }

    public String getName(){
        return name;
    }

    public String getCapbac(){
        return casi;
    }


    public int getImage(){
        return  image;
    }

    public int getPos(){
        return  pos;
    }
}
