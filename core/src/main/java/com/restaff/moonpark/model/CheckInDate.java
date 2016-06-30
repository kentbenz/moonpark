package com.restaff.moonpark.model;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by DHLE on 6/29/2016.
 */

/**
 * This class contains all information when the vehicle check in the park
 */
public class CheckInDate extends MoonParkDate {

    public CheckInDate(Date date) {
        super(date);
    }

    /**
     * Get total hours to end of day
     * @return total of hour from check in to end of day
     */
    public long getTotalHoursToEndOfDay() {
        return (getEndOfDay().getTime() - getDate().getTime()) / 1000 / 60 / 60;
    }

    /**
     * Get total minutes to end of day
     * @return total of minute from check in to end of day
     */
    public long getTotalMinutesToEndOfDay() {
        return (getEndOfDay().getTime() - getDate().getTime()) / 1000 / 60;
    }
}
