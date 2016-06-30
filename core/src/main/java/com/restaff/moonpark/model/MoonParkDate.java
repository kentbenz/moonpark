package com.restaff.moonpark.model;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by DHLE on 6/29/2016.
 */
public class MoonParkDate {

    static final long TOTAL_HOURS_IN_DATE = 24;
    static final long TOTAL_MINUTES_IN_DATE = TOTAL_HOURS_IN_DATE * 60;

    private Date date;

    public MoonParkDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /**
     *
     * @return true if date is not null and false if date is null
     */
    public boolean isValid() {
        return date != null;
    }

    public long getTotalHoursInDay() {
        return TOTAL_HOURS_IN_DATE;
    }

    public long getTotalMinutesInDay() {
        return TOTAL_MINUTES_IN_DATE;
    }

    public Date getStartOfDay() {
        return DateUtils.truncate(getDate(), Calendar.DATE);
    }

    public Date getEndOfDay() {
        return DateUtils.addMilliseconds(DateUtils.ceiling(getDate(), Calendar.DATE), -1);
    }

    public boolean isWeekday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        boolean isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= Calendar.FRIDAY));

        return isWeekday;
    }

    public boolean isSunday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SUNDAY;
    }

    public MoonParkTime getMoonParkTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new MoonParkTime(hour, minute);
    }

}
