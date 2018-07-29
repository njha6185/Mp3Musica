package com.example.nitishkumar.mp3musica;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NITISH KUMAR on 12-07-2018.
 * This is Custom ArrayAdapter Class
 */

public class AudioAdapter extends BaseAdapter{

    private ArrayList<AudioModel> songs;
    private LayoutInflater songInf;

    public AudioAdapter(Context c, ArrayList<AudioModel> songs) {
        this.songs = songs;
        this.songInf = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RelativeLayout songLayout = (RelativeLayout) songInf.inflate(R.layout.list_of_songs, viewGroup, false);
        TextView songview = (TextView)songLayout.findViewById(R.id.song_title);
        songview.setSelected(true);
        TextView artistView = (TextView)songLayout.findViewById(R.id.song_artist);
        artistView.setSelected(true);
        ImageView coverImageView = (ImageView)songLayout.findViewById(R.id.imageOfSong);
        AudioModel audioModel = songs.get(i);
        songview.setText(audioModel.getTitle());
        artistView.setText(audioModel.getArtist());
        if (audioModel.getImage() != null)
        {
            coverImageView.setImageBitmap(audioModel.getImage());
        }
        songLayout.setTag(i);
        return songLayout;
    }
}