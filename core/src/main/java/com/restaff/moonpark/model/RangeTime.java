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

    public int getTotalMinutes() {
        int hoursBetween = endTime.getHour() - startTime.getHour();
        int minutesBetween = endTime.getMinute() - startTime.getMinute();
        return hoursBetween * 60 + minutesBetween;
    }

    public int getTotalMinutesTo(MoonParkTime moonParkTime) {
        int hoursBetween = moonParkTime.getHour() - startTime.getHour();
        int minutesBetween = moonParkTime.getMinute() - startTime.getMinute();
        return hoursBetween * 60 + minutesBetween;
    }

    public int getTotalMinutesFrom(MoonParkTime moonParkTime) {
        int hoursBetween = endTime.getHour() - moonParkTime.getHour();
        int minutesBetween = endTime.getMinute() - moonParkTime.getMinute();
        return hoursBetween * 60 + minutesBetween;
    }

    public int getTotalMinutes(MoonParkTime moonParkTimeStart, MoonParkTime moonParkTimeEnd) {
        int hoursBetween = moonParkTimeEnd.getHour() - moonParkTimeStart.getHour();
        int minutesBetween = moonParkTimeEnd.getMinute() - moonParkTimeStart.getMinute();
        return hoursBetween * 60 + minutesBetween;
    }

    public void setRangeTimesDiscount(List<RangeTime> rangeTimesDiscount) {
        this.rangeTimesDiscount = rangeTimesDiscount;
    }

    public void addRangeTimeDiscount(RangeTime rangeTimeDiscount) {
        if (this.rangeTimesDiscount == null) {
            this.rangeTimesDiscount = new ArrayList<RangeTime>();
        }

        this.rangeTimesDiscount.add(rangeTimeDiscount);
    }
}
