package com.restaff.moonpark.model;

import com.restaff.moonpark.util.MoonParkUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by DHLE on 6/29/2016.
 */
public class ParkZone1 extends ParkZone {

    static final Float PRICE_PER_HOUR = 60F;
    static final Float PRICE_PER_MINUTE = PRICE_PER_HOUR / 60;

    public ParkZone1(String code) {
        super(code);
    }

    public Float calculatePrice(RangeDate rangeDate) throws MoonParkException {

        if (rangeDate.isCheckOutSameCheckInDate()) {
            long totalMinutesStayInPark = rangeDate.getTotalMinutes();
            return totalMinutesStayInPark * PRICE_PER_MINUTE;
        }

        long totalMinutesStayInCheckInDay = rangeDate.getCheckInDate().getTotalMinutesToEndDay();
        long totalMinutesStayInCheckOutDay = rangeDate.getCheckOutDate().getTotalMinutesFromStartDay();
        long totalMinutesStayDays = 0;
        List<StayInParkDate> stayInParkDateList = rangeDate.getStayInParkExclusiveInOut();
        if (stayInParkDateList == null || stayInParkDateList.isEmpty()) {
            totalMinutesStayDays = 0;
        } else {
            for (StayInParkDate stayInParkDate : stayInParkDateList) {
                totalMinutesStayDays += stayInParkDate.getTotalMinutesInDay();
            }
        }

        return (totalMinutesStayInCheckInDay + totalMinutesStayDays + totalMinutesStayInCheckOutDay) * PRICE_PER_MINUTE;
    }
}
