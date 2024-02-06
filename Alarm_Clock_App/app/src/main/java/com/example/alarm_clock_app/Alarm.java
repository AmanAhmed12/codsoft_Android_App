package com.example.alarm_clock_app;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;
import android.widget.Switch;

public class Alarm {
    private String time;
    private String alarmTone;
    private Switch status;

    public Alarm(String time, String alarmTone, Switch status) {
        this.time = time;
        this.alarmTone = alarmTone;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public String getAlarmTone() {
        return alarmTone;
    }

    public Switch getStatus() {
        return status;
    }
    public void setStatus(Switch status) {
        this.status=status;
        status.setChecked(true);

    }

    public static Alarm fromString(String alarmString, Switch status) {
        String[] parts = alarmString.split(",");
        if (parts.length == 3) {
            return new Alarm(parts[0], parts[1], status);
        }
        return null;
    }

}
