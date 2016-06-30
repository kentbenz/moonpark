package com.restaff.moonpark.model;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by DHLE on 6/29/2016.
 */
public class CheckInDate extends MoonParkDate {

    public CheckInDate(Date date) {
        super(date);
    }

    public long getTotalHoursToEndDay() {
        return (getEndOfDay().getTime() - getDate().getTime()) / 1000 / 60 / 60;
    }

    public long getTotalMinutesToEndDay() {
        return (getEndOfDay().getTime() - getDate().getTime()) / 1000 / 60;
    }
}
