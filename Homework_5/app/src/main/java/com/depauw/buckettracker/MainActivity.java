package com.depauw.buckettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.depauw.buckettracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    //Constants used for timer functionality
    private static final int DEFAULT_NUM_MINS = 20;
    private static final int MILLIS_PER_MIN = 60000;
    private static final int MILLIS_PER_SEC = 1000;
    private static final int SEC_PER_MIN = 60;
    private CountDownTimer countDownTimer;

    //Helper function. Used to swap team selection
    //Change team name and score color according to official's selection
    private void swapTeamSelection(){
        int viewRedColorInt = getResources().getColor(R.color.red);
        ColorStateList viewRedColor = ColorStateList.valueOf(viewRedColorInt);

        int viewBlackColorInt = getResources().getColor(R.color.black);
        ColorStateList viewBLackColor = ColorStateList.valueOf(viewBlackColorInt);
        if(!binding.toggleIsGuest.isChecked()) {
            //Set Home team's text and score color to red
            binding.textviewHomeScore.setTextColor(viewRedColor);
            binding.labelHome.setTextColor(viewRedColor);

            //Set Guest team's text and score color to black
            binding.textviewGuestScore.setTextColor(viewBLackColor);
            binding.labelGuest.setTextColor(viewBLackColor);
        }
        else{
            //Set Guest team's text and score color to red
            binding.textviewGuestScore.setTextColor(viewRedColor);
            binding.labelGuest.setTextColor(viewRedColor);

            //Set Home team's text and score color to black
            binding.textviewHomeScore.setTextColor(viewBLackColor);
            binding.labelHome.setTextColor(viewBLackColor);
        }
    }

    //Listeners
    //Top Portion
    private View.OnLongClickListener button_addScore_onLongClickListener = new View.OnLongClickListener(){
        public boolean onLongClick(View v){
            int currScore = 0;
            if(binding.checkboxAddOne.isChecked()) {
                currScore += 1;
                binding.checkboxAddOne.setChecked(false);
            }
            if(binding.checkboxAddTwo.isChecked()) {
                currScore += 2;
                binding.checkboxAddTwo.setChecked(false);
            }
            if(binding.checkboxAddThree.isChecked()) {
                currScore += 3;
                binding.checkboxAddThree.setChecked(false);
            }

            if(binding.toggleIsGuest.getText().equals(binding.toggleIsGuest.getTextOff())){
                currScore += Integer.valueOf(binding.textviewHomeScore.getText().toString());
                binding.textviewHomeScore.setText(String.valueOf(currScore));
            }
            else{
                currScore += Integer.valueOf(binding.textviewGuestScore.getText().toString());
                binding.textviewGuestScore.setText(String.valueOf(currScore));
            }
            binding.toggleIsGuest.toggle();
            swapTeamSelection();
            return true;
        }
    };

    private View.OnClickListener toggle_isGuest_OnClickListener = new View.OnClickListener(){
        public void onClick(View v){
            swapTeamSelection();
        }
    };

    //Bottom Portion
    private View.OnClickListener switch_gameClock_OnClickListener = new View.OnClickListener(){
        public void onClick(View v){
            if(binding.switchGameClock.isChecked()){
                long startTime;
                if(binding.edittextNumMins.getText().toString().equals("0:00")){
                    setClockTime(DEFAULT_NUM_MINS, 0);
                    startTime = DEFAULT_NUM_MINS * MILLIS_PER_MIN;

                }
                else {
                    String currTime = binding.textviewTimeRemaining.getText().toString();
                    String[] splitted = currTime.split(":");
                    int min = Integer.valueOf(splitted[0]);
                    int sec = Integer.valueOf(splitted[1]);
                    startTime = min * MILLIS_PER_MIN + sec * MILLIS_PER_SEC;
                }
                countDownTimer = getNewTimer(startTime, MILLIS_PER_SEC);
            }
            else{
                countDownTimer.cancel();
                String currTime = binding.textviewTimeRemaining.getText().toString();
            }
        }
    };

    private View.OnClickListener button_set_time_onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String minText = binding.edittextNumMins.getText().toString();
            String secText = binding.edittextNumSecs.getText().toString();

            if(!minText.equals("") && !secText.equals("")) {
                int min = Integer.valueOf(minText);
                int sec = Integer.valueOf(secText);
                if (min < DEFAULT_NUM_MINS && sec < SEC_PER_MIN) {
                    setClockTime(min, sec);
                    if(countDownTimer != null) {
                        countDownTimer.cancel();
                        countDownTimer = null;
                    }
                    binding.switchGameClock.setChecked(false);
                }
            }
        }
    };

    private CountDownTimer getNewTimer(long timerLength, long tickLength){
        return new CountDownTimer(timerLength, tickLength) {
            @Override
            public void onTick(long millisUntilFinished) {
                String currTime = binding.textviewTimeRemaining.getText().toString();
                String[] splitted = currTime.split(":");
                int min = Integer.valueOf(splitted[0]);
                int sec = Integer.valueOf(splitted[1]);
                if(sec == 0){
                    min--;
                    sec = 59;
                }
                else sec --;
                setClockTime(min, sec);
            }

            @Override
            public void onFinish() {
                binding.switchGameClock.toggle();
            }
        }.start();
    }

    private void setClockTime(int min, int sec){
        String minText = (min >= 10) ? String.valueOf(min) : "0" + String.valueOf(min);
        String secText = (sec >= 10) ? String.valueOf(sec) : "0" + String.valueOf(sec);
        binding.textviewTimeRemaining.setText(new StringBuilder(minText).append(":").append(secText).toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonAddScore.setOnLongClickListener(button_addScore_onLongClickListener);
        binding.toggleIsGuest.setOnClickListener(toggle_isGuest_OnClickListener);
        binding.switchGameClock.setOnClickListener(switch_gameClock_OnClickListener);
        binding.buttonSetTime.setOnClickListener(button_set_time_onClickListener);
    }
}