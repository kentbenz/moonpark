package com.restaff.moonpark.model;

import java.util.List;

/**
 * Created by DHLE on 6/29/2016.
 */
public class ParkZone2 extends ParkZone {

    static final Float PRICE_PER_HOUR_ON_WEEKDAY = 100F;
    static final Float PRICE_PER_HOUR_ON_WEEKEND = 200F;

    public ParkZone2(String code) {
        super(code);
    }

    public Float calculatePrice(RangeDate rangeDate) throws MoonParkException {
        if (rangeDate.isCheckOutSameCheckInDate()) {
            long totalHoursStayInPark = rangeDate.getTotalHours();
            CheckInDate checkInDate = rangeDate.getCheckInDate();
            return totalHoursStayInPark * (checkInDate.isWeekday() ? PRICE_PER_HOUR_ON_WEEKDAY : PRICE_PER_HOUR_ON_WEEKEND);
        }

        CheckInDate checkInDate = rangeDate.getCheckInDate();
        long totalHoursStayInCheckInDay = checkInDate.getTotalHoursToEndDay();
        Float totalPriceInCheckInDay = totalHoursStayInCheckInDay * (checkInDate.isWeekday() ? PRICE_PER_HOUR_ON_WEEKDAY : PRICE_PER_HOUR_ON_WEEKEND);

        CheckOutDate checkOutDate = rangeDate.getCheckOutDate();
        long totalHoursStayInCheckOutDay = checkOutDate.getTotalHoursFromStartDay();
        Float totalPriceInCheckOutDay = totalHoursStayInCheckOutDay * (checkOutDate.isWeekday() ? PRICE_PER_HOUR_ON_WEEKDAY : PRICE_PER_HOUR_ON_WEEKEND);

        Float totalPriceInStayDays = 0F;
        List<StayInParkDate> stayInParkDateList = rangeDate.getStayInParkExclusiveInOut();
        if (stayInParkDateList == null || stayInParkDateList.isEmpty()) {
            totalPriceInStayDays = 0F;
        } else {
            for (StayInParkDate stayInParkDate : stayInParkDateList) {
                long totalHours = stayInParkDate.getTotalHoursInDay();
                totalPriceInStayDays += totalHours * (stayInParkDate.isWeekday() ? PRICE_PER_HOUR_ON_WEEKDAY : PRICE_PER_HOUR_ON_WEEKEND);
            }
        }

        return totalPriceInCheckInDay + totalPriceInStayDays + totalPriceInCheckOutDay;
    }

}
