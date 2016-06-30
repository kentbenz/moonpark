package com.restaff.moonpark.model;

import com.restaff.moonpark.util.MoonParkUtil;
import org.apache.commons.lang3.time.DateUtils;

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

    public long getTotalHours() throws MoonParkException {
        return MoonParkUtil.getHours(checkInDate.getDate(), checkOutDate.getDate());
    }

    public long getTotalMinutes() throws MoonParkException {
        return MoonParkUtil.getMinutes(checkInDate.getDate(), checkOutDate.getDate());
    }

    public boolean isValid() {
        return checkInDate.isValid() && checkOutDate.isValid() && checkInDate.getDate().before(checkOutDate.getDate());
    }

    public boolean isCheckOutSameCheckInDate() {
        return MoonParkUtil.isSameDay(checkInDate.getDate(), checkOutDate.getDate());
    }

    public boolean isCheckOutNextCheckInDate() {
        Calendar inCa = Calendar.getInstance();
        inCa.setTime(checkInDate.getDate());

        Calendar outCa = Calendar.getInstance();
        outCa.setTime(checkOutDate.getDate());

        if (outCa.get(Calendar.DAY_OF_YEAR) - inCa.get(Calendar.DAY_OF_YEAR) == 1) {
            return true;
        }

        return false;

    }

    public List<StayInParkDate> getStayInParkExclusiveInOut() {
        if (isCheckOutSameCheckInDate() || isCheckOutNextCheckInDate()) {
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
