package com.example.nitishkumar.mp3musica;

import android.graphics.Bitmap;

/**
 * Created by NITISH KUMAR on 11-07-2018.
 */

public class AudioModel {

    private long id;
    private String title;
    private String artist;
    private Bitmap image;
    private String path;


    public AudioModel(long id, String title, String artist, Bitmap image, String path) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.image = image;
        this.path = path;
    }

    public void setID(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public long getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getPath() {
        return path;
    }
}
