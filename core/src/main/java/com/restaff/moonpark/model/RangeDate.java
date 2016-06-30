package com.restaff.moonpark.model;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by DHLE on 6/29/2016.
 */
public class RangeDate {
    private CheckInDate checkInDate;
    private CheckOutDate checkOutDate;

    public RangeDate(CheckInDate checkInDate, CheckOutDate checkOutDate) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public CheckInDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(CheckInDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public CheckOutDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(CheckOutDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    /**
     * Get total hours from check in to check out
     * @return the total hours in range
     * @throws MoonParkException
     */
    public long getTotalHours() throws MoonParkException {
        DateTime inJodaTime = new DateTime(checkInDate.getDate().getTime());
        DateTime outJodaTime = new DateTime(checkOutDate.getDate().getTime());

        return Hours.hoursBetween(inJodaTime, outJodaTime).getHours();
    }

    /**
     * Get total minutes from check in to check out
     * @return the total minutes in range
     * @throws MoonParkException
     */
    public long getTotalMinutes() throws MoonParkException {
        DateTime inJodaTime = new DateTime(checkInDate.getDate().getTime());
        DateTime outJodaTime = new DateTime(checkOutDate.getDate().getTime());

        return Minutes.minutesBetween(inJodaTime, outJodaTime).getMinutes();
    }

    /**
     * Check the validation of range
     * @return TRUE if range valid else return FALSE
     */
    public boolean isValid() {
        return checkInDate.isValid() && checkOutDate.isValid() && checkInDate.getDate().before(checkOutDate.getDate());
    }

    /**
     * Check the check in and check out are same day
     * @return TRUE if check in and check out are same day else return FALSE
     */
    public boolean isCheckOutSameCheckInDate() {
        if (checkInDate.getDate() == null || checkOutDate.getDate() == null) {
            return false;
        }

        return DateUtils.isSameDay(checkInDate.getDate(), checkOutDate.getDate());
    }

    /**
     * Get all days stays in park between check in and check out but exclusive check in and check out
     * @return list days stay in park exclusive check in and check out
     */
    public List<StayInParkDate> getStayInParkExclusiveInOut() {
        if (isCheckOutSameCheckInDate()) {
            return null;
        }

        Calendar start = Calendar.getInstance();

        start.setTime(checkInDate.getDate());

        Calendar end = Calendar.getInstance();
        end.setTime(checkOutDate.getDate());

        List<StayInParkDate> result = new ArrayList<StayInParkDate>();
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            if (DateUtils.isSameDay(date, checkInDate.getDate())) {
                continue;
            }

            if (DateUtils.isSameDay(date, checkOutDate.getDate())) {
                continue;
            }

            result.add(new StayInParkDate(date));
        }

        return result;
    }

}
