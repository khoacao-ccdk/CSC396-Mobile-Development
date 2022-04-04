package com.depauw.jukebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.depauw.jukebox.databinding.ActivityPlayerBinding;

import android.graphics.Color;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import java.io.IOException;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;

    private static final int MAX_COLOR_INTENSITY = 255;
    private MediaPlayer media;
    private float songOneAverage = 0 ;
    private float songTwoAverage = 0;
    private float songThreeAverage = 0;

    //Listeners
    //Top Portion
    private SeekBar.OnSeekBarChangeListener seekbars_onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener(){
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            switch (seekBar.getId()){
                case R.id.seekbar_red:
                    binding.textviewRed.setText(String.valueOf(i));
                    break;
                case R.id.seekbar_green:
                    binding.textviewGreen.setText(String.valueOf(i));
                    break;
                case R.id.seekbar_blue:
                    binding.textviewBlue.setText(String.valueOf(i));
                    break;
            }
            //Gẹt current color setting from seekbars
            float red = (float) (binding.seekbarRed.getProgress() / (float)MAX_COLOR_INTENSITY);
            float green = (float) (binding.seekbarGreen.getProgress() / (float)MAX_COLOR_INTENSITY);
            float blue = (float) (binding.seekbarBlue.getProgress() / (float)MAX_COLOR_INTENSITY);

            //Set color setting to View
            int viewColor = Color.argb(1.0f, red, green, blue);
            binding.constraintlayout.setBackgroundColor(viewColor);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //Middle Portion
    private RadioGroup.OnCheckedChangeListener radio_group_songs_onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            //Switch song and cover image base on the selected radio button
            setTrackImage(i);
            media.stop();
            media = MediaPlayer.create(PlayerActivity.this, getSelectedTrackId(i));
            media.start();
        }
    };

    private SeekBar.OnSeekBarChangeListener seekbar_song_position_onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            int songPosition = binding.seekbarSongPosition.getProgress() *  media.getDuration() / 100;
            media.seekTo(songPosition);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //Bottom Portion
    private View.OnClickListener button_cast_vote_onCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (binding.ratingbarVoterRating.getRating() == 0)
                return;

            switch (binding.radiogroupSongs.getCheckedRadioButtonId()){
                case R.id.radio_song1:{
                    int voteCount = Integer.valueOf(binding.textviewNumVotes1.getText().toString());
                    songOneAverage = (binding.ratingbarVoterRating.getRating() + songOneAverage * voteCount) / (voteCount + 1);
                    voteCount++;

                    //Update average progress bar and vote counter for song 1
                    binding.progressbarAverageRating1.setProgress(Math.round(songOneAverage));
                    binding.textviewNumVotes1.setText(String.valueOf(voteCount));
                    break;
                }
                case R.id.radio_song2:{
                    int voteCount = Integer.valueOf(binding.textviewNumVotes2.getText().toString());
                    songTwoAverage = (binding.ratingbarVoterRating.getRating() + songTwoAverage * voteCount) / (voteCount + 1);
                    voteCount++;

                    //Update average progress bar and vote counter for song 2
                    binding.progressbarAverageRating2.setProgress(Math.round(songTwoAverage));
                    binding.textviewNumVotes2.setText(String.valueOf(voteCount));
                    break;
                }
                case R.id.radio_song3:{
                    int voteCount = Integer.valueOf(binding.textviewNumVotes3.getText().toString());
                    songThreeAverage = (binding.ratingbarVoterRating.getRating() + songThreeAverage * voteCount) / (voteCount + 1);
                    voteCount++;

                    //Update average progress bar and vote counter for song 1
                    binding.progressbarAverageRating3.setProgress(Math.round(songThreeAverage));
                    binding.textviewNumVotes3.setText(String.valueOf(voteCount));
                    break;
                }
            }
            binding.ratingbarVoterRating.setProgress(0);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.seekbarRed.setOnSeekBarChangeListener(seekbars_onSeekBarChangeListener);
        binding.seekbarGreen.setOnSeekBarChangeListener(seekbars_onSeekBarChangeListener);
        binding.seekbarBlue.setOnSeekBarChangeListener(seekbars_onSeekBarChangeListener);
        binding.seekbarSongPosition.setOnSeekBarChangeListener(seekbar_song_position_onSeekBarChangeListener);
        binding.radiogroupSongs.setOnCheckedChangeListener(radio_group_songs_onCheckedChangeListener);
        binding.buttonCastVote.setOnClickListener(button_cast_vote_onCLickListener);

        //Set default color to white and values of the three color seekbars to 255
        binding.seekbarRed.setProgress(MAX_COLOR_INTENSITY);
        binding.seekbarGreen.setProgress(MAX_COLOR_INTENSITY);
        binding.seekbarBlue.setProgress(MAX_COLOR_INTENSITY);

        //Get Id of the default song selected
        int checkedButtonId = binding.radiogroupSongs.getCheckedRadioButtonId();

        //Start playing the default song and set the cover image as soon as the app starts
        setTrackImage(checkedButtonId);
        media = MediaPlayer.create(this, getSelectedTrackId(checkedButtonId));
        media.start();
    }

    //Helper functions
    private int getSelectedTrackId(int radioButtonId){
        switch (radioButtonId){
            case R.id.radio_song1:
                return R.raw.track1;
            case  R.id.radio_song2:
                return R.raw.track2;
            case R.id.radio_song3:
                return R.raw.track3;
        }
        return -1;
    }

    private void setTrackImage(int radioButtonId){
        //Switch imageView based on selected track
        Drawable trackImage;
        switch(radioButtonId){
            case R.id.radio_song1:
                trackImage = getResources().getDrawable(R.drawable.track1);
                break;
            case R.id.radio_song2:
                trackImage = getResources().getDrawable(R.drawable.track2);
                break;
            default:
                trackImage = getResources().getDrawable(R.drawable.track3);
        }
        binding.imageviewAlbumCover.setImageDrawable(trackImage);
    }
}

