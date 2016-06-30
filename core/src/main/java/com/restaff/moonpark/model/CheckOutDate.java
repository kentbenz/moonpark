package com.restaff.moonpark.model;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by DHLE on 6/26/2016.
 */

/**
 * This class contains all information when the vehicle check out the park
 */
public class CheckOutDate extends MoonParkDate {

    public CheckOutDate(Date date) {
        super(date);
    }

    /**
     * Get total hours from start of day
     * @return total hours from start of day
     */
    public long getTotalHoursFromStartOfDay() {
        return (getDate().getTime() - getStartOfDay().getTime()) / 1000 / 60 / 60;
    }

    /**
     * Get total minutes from start of day
     * @return total minutes from start of day
     */
    public long getTotalMinutesFromStartOfDay() {
        return (getDate().getTime() - getStartOfDay().getTime()) / 1000 / 60;
    }


}
