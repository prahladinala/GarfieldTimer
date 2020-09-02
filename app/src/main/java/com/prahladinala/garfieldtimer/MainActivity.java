package com.prahladinala.garfieldtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        timerSeekBar.setEnabled(true);
        goButton.setText("GO!");
        counterIsActive = false;
    }

    public void buttonClicked(View view){

        if(counterIsActive){
            resetTimer();
        } else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);

            goButton.setText("STOP!");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() *1000  + 100,1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l/ 1000);
                }

                @Override
                public void onFinish() {

                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                    mplayer.start();
                    resetTimer();

                }
            }.start();
        }

    }

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);
//        if(secondString.equals("0")){
//            secondString = "00";
//        }
        if(seconds <= 9){
            secondString = "0"+secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = findViewById(R.id.seekBar);
        timerTextView = findViewById(R.id.timerTextView);
        goButton = findViewById(R.id.button);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}