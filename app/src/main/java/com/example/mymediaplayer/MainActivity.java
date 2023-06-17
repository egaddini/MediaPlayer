package com.example.mymediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AudioManager audioManager;
    MediaPlayer mediaPlayer;
    ImageButton bntPlay, bntStop, bntPause;
    SeekBar seekBarTimer, seekBarVolume;
    TextView textView;
    private Timer timerCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.seminovo);
        textView = findViewById(R.id.textView);
        seekBarTimer = findViewById(R.id.seekBar);
        bntPause = findViewById(R.id.bntPause);
        bntPlay = findViewById(R.id.bntPlay);
        bntPause = findViewById(R.id.bntStop);
        bntPause.setOnClickListener(this);
        bntPlay.setOnClickListener(this);
        bntPause.setOnClickListener(this);
        seekBarVolume = findViewById(R.id.seekBarVolume);

        seekBarTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    mediaPlayer.seekTo(i);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        inicializaSeekBar();
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
//                    audioManager.;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void inicializaSeekBar() {
        int volumeMaximo = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBarVolume.setMax(volumeMaximo);
        seekBarVolume.setProgress(volume);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bntPlay:
                mediaPlayer.start();
                timerCounter();
                break;
            case R.id.bntPause:
                mediaPlayer.pause();
                break;
            case R.id.bntStop:
                mediaPlayer.stop();
                break;
        }
    }

    public String convertDurationMillis(Integer getDurationInMillis) {

        int getDurationMillis = getDurationInMillis;
        String convertHours = String.format("%02d", TimeUnit.MILLISECONDS.toHours(getDurationMillis));
        String convertMinutes = String.format("%02d", TimeUnit.MILLISECONDS.toMinutes(getDurationMillis));
        String convertSeconds = String.format("%02d", TimeUnit.MILLISECONDS.toSeconds(getDurationMillis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getDurationMillis)));

        return  convertHours + ":" + convertMinutes + ":" + convertSeconds;
    }

    public void atualizaTimer() {
        textView.setText(convertDurationMillis(mediaPlayer.getCurrentPosition())+"/"+convertDurationMillis(mediaPlayer.getDuration()));
        seekBarTimer.setMax(mediaPlayer.getDuration());
    }

    private void timerCounter() {
        timerCounter = new Timer();
        timerCounter.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                atualizaTimer();
                                seekBarTimer.setProgress(mediaPlayer.getCurrentPosition());

                            }
                        });
                    }
                }, 0, 100);
    }

}