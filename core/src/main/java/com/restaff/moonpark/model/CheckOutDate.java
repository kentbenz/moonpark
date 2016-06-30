package com.restaff.moonpark.model;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by DHLE on 6/26/2016.
 */
public class CheckOutDate extends MoonParkDate {

    public CheckOutDate(Date date) {
        super(date);
    }

    public long getTotalHoursFromStartDay() {
        return (getDate().getTime() - getStartOfDay().getTime()) / 1000 / 60 / 60;
    }

    public long getTotalMinutesFromStartDay() {
        return (getDate().getTime() - getStartOfDay().getTime()) / 1000 / 60;
    }


}
