package com.restaff.moonpark.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DHLE on 6/29/2016.
 */
public class RangeTime {

    private MoonParkTime startTime;
    private MoonParkTime endTime;
    private List<RangeTime> rangeTimesDiscount;

    public RangeTime(MoonParkTime startTime, MoonParkTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.rangeTimesDiscount = new ArrayList<RangeTime>();
    }

    public MoonParkTime getStartTime() {
        return startTime;
    }

    public void setStartTime(MoonParkTime startTime) {
        this.startTime = startTime;
    }

    public MoonParkTime getEndTime() {
        return endTime;
    }

    public void setEndTime(MoonParkTime endTime) {
        this.endTime = endTime;
    }

    public List<RangeTime> getRangeTimesDiscount() {
        return rangeTimesDiscount;
    }

    /**
     * Get total minutes between 2 RangeTime
     * @return total minutes of range
     */
    public int getTotalMinutes() {
        int hoursBetween = endTime.getHour() - startTime.getHour();
        int minutesBetween = endTime.getMinute() - startTime.getMinute();
        return hoursBetween * 60 + minutesBetween;
    }

    /**
     * Get total minutes from check in time to other RangeTime
     * @param moonParkTime
     * @return total minutes
     */
    public int getTotalMinutesTo(MoonParkTime moonParkTime) {
        int hoursBetween = moonParkTime.getHour() - startTime.getHour();
        int minutesBetween = moonParkTime.getMinute() - startTime.getMinute();
        return hoursBetween * 60 + minutesBetween;
    }

    /**
     * Get total minutes from other RangeTime to check out time
     * @param moonParkTime
     * @return total minutes
     */
    public int getTotalMinutesFrom(MoonParkTime moonParkTime) {
        int hoursBetween = endTime.getHour() - moonParkTime.getHour();
        int minutesBetween = endTime.getMinute() - moonParkTime.getMinute();
        return hoursBetween * 60 + minutesBetween;
    }

    /**
     * Get total minutes between 2 RangeTime
     * @param moonParkTimeStart
     * @param moonParkTimeEnd
     * @return total minutes
     */
    public int getTotalMinutes(MoonParkTime moonParkTimeStart, MoonParkTime moonParkTimeEnd) {
        int hoursBetween = moonParkTimeEnd.getHour() - moonParkTimeStart.getHour();
        int minutesBetween = moonParkTimeEnd.getMinute() - moonParkTimeStart.getMinute();
        return hoursBetween * 60 + minutesBetween;
    }

    public void setRangeTimesDiscount(List<RangeTime> rangeTimesDiscount) {
        this.rangeTimesDiscount = rangeTimesDiscount;
    }

    /**
     * Add RangeTime discounr to RangeTime
     * @param rangeTimeDiscount
     */
    public void addRangeTimeDiscount(RangeTime rangeTimeDiscount) {
        if (this.rangeTimesDiscount == null) {
            this.rangeTimesDiscount = new ArrayList<RangeTime>();
        }

        this.rangeTimesDiscount.add(rangeTimeDiscount);
    }
}
