package com.example.nitishkumar.mp3musica;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SongPlayActivity extends AppCompatActivity {

    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play);
        getSongPosition();
    }

    public void getSongPosition()
    {
        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position", 0);
        Toast.makeText(this, "position : "+ position, Toast.LENGTH_SHORT).show();
    }
}
