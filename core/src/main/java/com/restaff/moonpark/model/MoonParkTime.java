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

    /**
     * Get total minutes of time
     * @return total minutes of time
     */
    public int getTotalMinutes() {
        return hour * 60 + minute;
    }

    /**
     * Check the validation of time
     * @return TRUE if time is valid else return FALSE
     */
    public boolean isValid() {
        return hour >= MIN_HOUR && hour <= MAX_HOUR && minute >= MIN_MUNUTE && minute <= MAX_MINUTE;
    }

}
