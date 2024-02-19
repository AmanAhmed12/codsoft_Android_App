package com.example.alarm_clock_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.util.Log;

import android.os.Bundle;
import android.media.MediaPlayer;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;

public class AlarmDisplay extends AppCompatActivity {
    public  SharedPreferences pref;
    public SharedPreferences.Editor editor;
    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_display);

        pref = this.getSharedPreferences("Music", 0);
        editor = pref.edit();
       int resId=SetAlarm.getTone();
        try {
            if(resId==0){
                music= MediaPlayer.create(this, R.raw.bgm1);
            }
            else{
                music = MediaPlayer.create(this, resId);
            }

            if (music != null) {
                music.setLooping(true);
                music.start();
            } else {

                Log.e("AlarmDisplay", "Failed to create MediaPlayer");
                Toast.makeText(this, "Failed to initialize background music", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Error initializing background music", Toast.LENGTH_SHORT).show();
        }

        Button close=findViewById(R.id.btnDismiss);
        close.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_green));
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                Intent intent=new Intent(AlarmDisplay.this, MainActivity.class);
                startActivity(intent);

            }
        });


        Button snooze = findViewById(R.id.btnSnooze);
        snooze.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_green));
        snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (music != null && music.isPlaying()) {
                    music.pause();
                }


                Intent intent = new Intent(AlarmDisplay.this, MainActivity.class);
                startActivity(intent);


                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // Resume playing the music
                                if (music != null) {
                                    music.start();
                                }


                                Intent intent = new Intent(AlarmDisplay.this, AlarmDisplay.class);
                                startActivity(intent);
                                finish();
                            }
                        },
                        2 * 60 * 1000
                );
            }
        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (music != null) {
            music.release();
            music = null;
        }
    }




}
