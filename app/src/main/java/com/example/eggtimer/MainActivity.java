package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timer;
    SeekBar setTime;
    Boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer;

    public void resetTimer (){
        timer.setText("0:30");
        setTime.setProgress(30);
        setTime.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("GO!");
        counterIsActive = false;
    }

    public void startCount (View view){

        if (counterIsActive) {
            resetTimer();
        } else {
            counterIsActive = true;
            timer.setEnabled(false);
            goButton.setText("Stop");
            countDownTimer = new CountDownTimer(setTime.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mplayer.start();
                    resetTimer();
                }
            }.start();
        }

    }

    public void updateTimer (int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds =  secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);
        if (seconds <= 9 ) {
            secondString = "0" + secondString;
        }

        timer.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTime = (SeekBar) findViewById(R.id.setTime);
        timer = (TextView) findViewById(R.id.countdown);
        goButton = (Button) findViewById(R.id.button);
        setTime.setMax(600);
        setTime.setProgress(30);

        setTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
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
