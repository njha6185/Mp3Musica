package com.example.nitishkumar.mp3musica;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SongPlayActivity extends AppCompatActivity implements View.OnClickListener{

    private int position;
    private ArrayList<AudioModel> songlistObject;
    private TextView titleSongText;
    private TextView artistSongText;
    private ImageView alubumArtImage;
    private TextView totalSongTimeText;
    private TextView currentSongTimeText;
    private SeekBar seekBarForSong;
    private Button playSong;
    private Button nextSong;
    private Button previousSong;
    private boolean playPauseStaus;
    private Button songListActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play);

        init();
        getSongPosition();


        playSong.setOnClickListener(this);
        nextSong.setOnClickListener(this);
        previousSong.setOnClickListener(this);
        songListActivityButton.setOnClickListener(this);
    }
/********************* IT recieves and extract data Coming from List Activity ********************/
    public void getSongPosition()
    {
        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position", 0);
        songlistObject = bundle.getParcelableArrayList("object");
        Toast.makeText(this, "position : "+ position, Toast.LENGTH_SHORT).show();
    }
/************************** It Set Different Song Data to their Respective Views **************/
    public void setSongDetailsToUI()
    {
        AudioModel audioModel = songlistObject.get(position);
        titleSongText.setText(audioModel.getTitle());
        artistSongText.setText(audioModel.getArtist());
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(audioModel.getPath());
        try
        {
            byte[] art = metadataRetriever.getEmbeddedPicture();
            Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
            alubumArtImage.setImageBitmap(songImage);
        }
        catch (Exception e)
        {
            alubumArtImage.setImageResource(R.drawable.ic_music_note_black_24dp);
        }
        String duration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeduration = Long.parseLong(duration);
        totalSongTimeText.setText(convertMiliSeconfToMinute(timeduration));
        long currentTimeOfSong = getCurrentTimeOfSongSeekBar(timeduration);
        currentSongTimeText.setText(convertMiliSeconfToMinute(currentTimeOfSong));
    }
/********************* It takes SeekBar Current Time ******************************/
    public long getCurrentTimeOfSongSeekBar(long totalTime)
    {
        long currentTime = seekBarForSong.getProgress();
        currentTime = (totalTime/100) * currentTime;
        return currentTime;
    }
/*********************************** It Converts Millisecond To Hour, Minute, Second format amd return String***********/
    public String convertMiliSeconfToMinute(long timeduration)
    {
        String finalTimeString = "";
        String secondString = "";
        int hours = (int) (timeduration / (1000 * 60 * 60));
        int minutes = (int) (timeduration % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) (timeduration % (1000 * 60 * 60)) % (1000 * 60) / 1000;
        if (hours > 0)
        {
            finalTimeString = hours + ":";
        }
        if (seconds < 10)
        {
            secondString = "0" + seconds;
        }
        else
        {
            secondString = "" + seconds;
        }
        finalTimeString = finalTimeString + minutes + ":" + secondString;
        return finalTimeString;
    }
/*********************************** It initialises all Views ****************************************/
    public void init()
    {
        songlistObject = new ArrayList<>();
        titleSongText = (TextView)findViewById(R.id.titleTextView);
        artistSongText = (TextView)findViewById(R.id.artistTextView);
        alubumArtImage = (ImageView)findViewById(R.id.albumImageView);
        totalSongTimeText = (TextView)findViewById(R.id.totalSongTime);
        currentSongTimeText = (TextView)findViewById(R.id.currentSongTime);
        seekBarForSong = (SeekBar)findViewById(R.id.songSeekBar);
        seekBarForSong.setMax(100);
        playSong = (Button)findViewById(R.id.palyPauseButton);
        playSong.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        nextSong = (Button)findViewById(R.id.nextButton);
        previousSong = (Button)findViewById(R.id.previousButton);
        playPauseStaus = true;
        songListActivityButton = (Button)findViewById(R.id.songlistBackButton);
    }
/******************* this methods detects various Buttons Clicks ************************************/
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.palyPauseButton:
                if (playPauseStaus)
                {
                    playSong.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
                    playPauseStaus = false;
                }
                else
                {
                    playSong.setBackgroundResource(R.drawable.ic_pause_black_24dp);
                    playPauseStaus = true;
                }
                break;

            case R.id.nextButton:
                position++;
                if (position > (songlistObject.size() - 1))
                {
                    position = 0;
                }
                setSongDetailsToUI();
                break;

            case R.id.previousButton:
                position--;
                if (position < 0)
                {
                    position = songlistObject.size() - 1;
                }
                setSongDetailsToUI();
                break;

            case R.id.songlistBackButton:
                Intent intent = new Intent(SongPlayActivity.this, SongListActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }
}