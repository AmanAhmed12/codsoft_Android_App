package com.example.alarm_clock_app;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import java.util.Calendar;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import android.widget.AdapterView;
import java.lang.reflect.Field;

import java.util.List;
import android.content.SharedPreferences;

public class SetAlarm extends AppCompatActivity {
    private static ArrayAdapter<String> adapter;
    private  static ArrayList<Switch> switchList;
    private static SharedPreferences sharedPreferences;

    private int currentSwitchIndex = 0;
    private static ArrayList<Alarm> alarmList;
    private TimePickerDialog timePickerDialog;
private static int resId;

public static int  getTone(){
    return resId;
}

    public ArrayList getSwitch() {
        return switchList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        sharedPreferences = getSharedPreferences("AlarmPreferences", MODE_PRIVATE);

        Button btnSelectTime = findViewById(R.id.btnSelectTime);
        btnSelectTime.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_green));

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.myStatusBarColor));
        ListView list = findViewById(R.id.alarmList);
        alarmList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(adapter);

        switchList = new ArrayList<>();
        switchList.add(findViewById(R.id.status1));
        switchList.add(findViewById(R.id.status2));
        switchList.add(findViewById(R.id.status3));
        switchList.add(findViewById(R.id.status4));
        switchList.add(findViewById(R.id.status5));
        loadAlarmsFromSharedPreferences();

        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSwitchIndex < switchList.size()) {
                timePickerDialog = new TimePickerDialog(
                        SetAlarm.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String formattedMinute = (minute < 10) ? "0" + minute : String.valueOf(minute);
                                Toast.makeText(SetAlarm.this, "Alarm set on: " + hourOfDay + ":" + formattedMinute, Toast.LENGTH_SHORT).show();
                                String alarmTime = hourOfDay + ":" + formattedMinute;
                                Switch currentSwitch = switchList.get(currentSwitchIndex);

                                currentSwitch.setVisibility(View.VISIBLE);


                                Alarm newAlarm = new Alarm(alarmTime, "ringing", currentSwitch);
                                newAlarm.setStatus(currentSwitch);
                                alarmList.add(newAlarm);


                                adapter.add("⏰"+"  "+newAlarm.getTime());
                                saveAlarmsToSharedPreferences();

                                currentSwitchIndex++;

                                adapter.notifyDataSetChanged();

                            }
                        },
                        24,
                        0,
                        true
                );

                timePickerDialog.show();
            }  else {
            Toast.makeText(SetAlarm.this, "You can only add up to 5 alarms.", Toast.LENGTH_SHORT).show();
        }
            }
        });
        TextView timedisplay = findViewById(R.id.ViewTime);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);


                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String currentTime = sdf.format(calendar.getTime());
                timedisplay.setText(currentTime);

                if (check(currentTime)) {

                    Intent intent = new Intent(SetAlarm.this, AlarmDisplay.class);
                    startActivity(intent);
                     ringAlarm(currentTime);
                }

                handler.postDelayed(this, 60000);



            }
        }, 1000);

        Button btnSelectTone = findViewById(R.id.btnChooseTone);
        btnSelectTone.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_green));
        btnSelectTone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectToneDialog();
            }
        });

        Button btnDelete= findViewById(R.id.btnDel);
        btnDelete.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_green));
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               delete();
            }
        });





    }



    private void showSelectToneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_select_tone, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        ListView listViewTones = dialogView.findViewById(R.id.listViewTones);
        Button btnSelect = dialogView.findViewById(R.id.btnSelect);
        btnSelect.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_green));


        Field[] fields = R.raw.class.getFields();
        final List<String> toneList = new ArrayList<>();

        for (Field field : fields) {
            toneList.add(field.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, toneList);
        listViewTones.setAdapter(adapter);

        listViewTones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedItemPosition = listViewTones.getCheckedItemPosition();
                if (selectedItemPosition != ListView.INVALID_POSITION) {
                    String selectedTone = toneList.get(selectedItemPosition);
                     resId = getResources().getIdentifier(selectedTone, "raw", getPackageName());

                    Toast.makeText(SetAlarm.this, "Selected tone : " + selectedTone, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public boolean check(String currentTime) {
        for (Alarm alarm : alarmList) {
            if (alarm.getTime().equals(currentTime) && alarm.getStatus().isChecked()) {
                return true;
            }
        }
        return false;
    }

    private void saveAlarmsToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();

        for (int i = 0; i < alarmList.size(); i++) {
            Alarm alarm = alarmList.get(i);
            editor.putString("alarm_" + i, alarm.getTime());
            editor.putBoolean("alarm_status_" + i, alarm.getStatus().isChecked());
        }

        editor.apply();
    }

    private void loadAlarmsFromSharedPreferences() {
        alarmList.clear();


        currentSwitchIndex = 0;

        while (sharedPreferences.contains("alarm_" + currentSwitchIndex)) {
            String alarmTime = sharedPreferences.getString("alarm_" + currentSwitchIndex, "");
            boolean alarmStatus = sharedPreferences.getBoolean("alarm_status_" + currentSwitchIndex, false);
            Switch currentSwitch = switchList.get(currentSwitchIndex);


            Alarm alarm = new Alarm(alarmTime, "ringing", currentSwitch);
            alarm.getStatus().setChecked(alarmStatus);
            alarmList.add(alarm);


            currentSwitch.setVisibility(View.VISIBLE);
            currentSwitch.setChecked(false);

            currentSwitchIndex++;
        }


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        ListView list = findViewById(R.id.alarmList);
        list.setAdapter(adapter);


        for (Alarm alarm : alarmList) {
            adapter.add("⏰"+"  "+alarm.getTime());
        }

        adapter.notifyDataSetChanged();
    }

    public void delete() {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();


        alarmList.clear();
        currentSwitchIndex = 0;  // Reset the switch index


        adapter.clear();
        adapter.notifyDataSetChanged();


        for (Switch switchItem : switchList) {
            switchItem.setVisibility(View.GONE);
        }


        Toast.makeText(SetAlarm.this, "All alarms deleted", Toast.LENGTH_SHORT).show();
    }



    public void ringAlarm(String currentTime) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SetAlarm.this, "Ringing", Toast.LENGTH_LONG).show();

                        }
                    });


            }
        }





