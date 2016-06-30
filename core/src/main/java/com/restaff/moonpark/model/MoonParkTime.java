package com.restaff.moonpark.model;

/**
 * Created by DHLE on 6/29/2016.
 */
public class MoonParkTime {
    static final int MIN_HOUR = 0;
    static final int MAX_HOUR = 23;

    static final int MIN_MUNUTE = 0;
    static final int MAX_MINUTE = 59;

    private int hour;
    private int minute;

    public MoonParkTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getTotalMinutes() {
        return hour * 60 + minute;
    }

}
