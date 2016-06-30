package com.restaff.moonpark.model;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by DHLE on 6/29/2016.
 */

/**
 * This class contains all information when the vehicle stay in the park (from check in to check out)
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
     * Check the validation of day
     * @return true if date is not null and false if date is null
     */
    public boolean isValid() {
        return date != null;
    }

    /**
     * Get total hours in a day
     * @return 24 (because 1 day has 24 hours)
     */
    public long getTotalHoursInDay() {
        return TOTAL_HOURS_IN_DATE;
    }

    /**
     * Get total minutes in a day
     * @return 24 * 60 (because 1 day has 24 * 60 minutes)
     */
    public long getTotalMinutesInDay() {
        return TOTAL_MINUTES_IN_DATE;
    }

    /**
     * Get start of day
     * @return start of day (00:00:00 AM)
     */
    public Date getStartOfDay() {
        return DateUtils.truncate(getDate(), Calendar.DATE);
    }

    /**
     * Get end of day
     * @return end of day (23:59:59 PM)
     */
    public Date getEndOfDay() {
        return DateUtils.addMilliseconds(DateUtils.ceiling(getDate(), Calendar.DATE), -1);
    }

    /**
     * Check the day is weekday or not
     * @return TRUE if the day in weekday (from Monday to Friday) else return FALSE
     */
    public boolean isWeekday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        boolean isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= Calendar.FRIDAY));

        return isWeekday;
    }

    /**
     * Check the day is Sunday or not
     * @return TRUE if the day is Sunday else return FALSE
     */
    public boolean isSunday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * Get the time in day
     * @return MoonParkTime (ex: 19:08:30 PM)
     */
    public MoonParkTime getMoonParkTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new MoonParkTime(hour, minute);
    }

}
