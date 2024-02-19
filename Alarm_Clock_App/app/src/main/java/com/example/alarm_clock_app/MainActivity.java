package com.example.alarm_clock_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.core.content.ContextCompat;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       TextView Time=findViewById(R.id.Time);
        TextView Date=findViewById(R.id.DateVal);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.myStatusBarColor));
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Calendar calendar=Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                String time = String.format("%02d:%02d", hour, minute);
                String date = String.format("%02d-%02d-%02d", day, month,year);
                Time.setText(time);
                Date.setText(date);


                handler.postDelayed(this, 60000);



            }
        }, 1000);






        Button btnSetAlarm=findViewById(R.id.btnSetAlarm);
        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SetAlarm.class);
                startActivity(intent);

            }
        });

    }
}