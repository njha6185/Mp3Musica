package com.example.nitishkumar.mp3musica;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NITISH KUMAR on 11-07-2018.
 */

public class AudioModel implements Parcelable{

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

    protected AudioModel(Parcel in) {
        id = in.readLong();
        title = in.readString();
        artist = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
        path = in.readString();
    }

    public static final Creator<AudioModel> CREATOR = new Creator<AudioModel>() {
        @Override
        public AudioModel createFromParcel(Parcel in) {
            return new AudioModel(in);
        }

        @Override
        public AudioModel[] newArray(int size) {
            return new AudioModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(artist);
        parcel.writeParcelable(image, i);
        parcel.writeString(path);
    }
}
