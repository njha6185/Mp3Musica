package com.example.nitishkumar.mp3musica;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SongListActivity extends AppCompatActivity implements OnItemClickListener{

    private ArrayList<AudioModel> songlist;
    private ListView songView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);
        if (isStoragePermissionGranted())
        {
            init();
            songView.setOnItemClickListener(this);
            getSongList();
            sortAndSetSongToListView();
        }
        else
        {
            this.finish();
        }
    }
/*************** initialiases Views *************************************/
    public void init()
    {
        songView = (ListView)findViewById(R.id.listOfSong);
        songlist = new ArrayList<>();
    }
/************************* fetch songs from external storage *******************************/
    public void getSongList()
    {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri;
        musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst())
        {
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int coverImageColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            int pathColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                long thisID = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                Bitmap thisImage = null;
                if (coverImageColumn != -1)
                {
                    String thisCoverImage = musicCursor.getString(coverImageColumn);
                    thisImage = BitmapFactory.decodeFile(thisCoverImage);
                }
                String thispath = musicCursor.getString(pathColumn);
                songlist.add(new AudioModel(thisID, thisTitle, thisArtist, thisImage, thispath));
            }
            while (musicCursor.moveToNext());
            musicCursor.close();
        }
    }
/************************* It sortlist the song and list it in list View *************/
    public void sortAndSetSongToListView()
    {
        Collections.sort(songlist, new Comparator<AudioModel>() {
            @Override
            public int compare(AudioModel audioModel, AudioModel t1) {
                return audioModel.getTitle().compareTo(t1.getTitle());
            }
        });
        AudioAdapter audioAdapter = new AudioAdapter(this, songlist);
        songView.setAdapter(audioAdapter);
    }
/******************************** Check storge permission is granted or Not ********************************/
    public boolean isStoragePermissionGranted()
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else
        {
            return true;
        }
    }
/******************************* Check whether Storage permission is granted or not **************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        else
        {
            this.finish();
        }
    }
/*********************** it detects the clicked item from list and send to playSomg activity **********************/
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(SongListActivity.this, SongPlayActivity.class);
        intent.putExtra("position", i);
        intent.putExtra("object", songlist);
        startActivity(intent);
    }
}