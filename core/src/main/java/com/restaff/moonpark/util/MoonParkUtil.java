package com.restaff.moonpark.util;

import com.restaff.moonpark.model.*;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by DHLE on 6/29/2016.
 */
public class MoonParkUtil {

    static final String WEEKDAY_HOURS = "WEEKDAY_HOURS";
    static final String WEEKDAY_MINUTES = "WEEKDAY_MINUTES";
    static final String WEEKEND_HOURS = "WEEKEND_HOURS";
    static final String WEEKEND_MINUTES = "WEEKEND_MINUTES";
    static final String WEEKEND = "WEEKEND";
    static final String WEEKDAY = "WEEKDAY";
    static final String FIRST = "FIRST";
    static final String LAST = "LAST";

    public static void checkDateRangeValidation(Date in, Date out) throws MoonParkException {
        if (in == null) {
            throw new MoonParkException(MoonParkErrorCode.NO_CHECKIN_VALUE);
        }

        if (out == null) {
            throw new MoonParkException(MoonParkErrorCode.NO_CHECKOUT_VALUE);
        }

        if (out.before(in)) {
            throw new MoonParkException(MoonParkErrorCode.CHECKOUT_BEFORE_CHECKIN);
        }
    }

    public static void checkDateValidation(Date date) throws MoonParkException {
        if (date == null) {
            throw new MoonParkException(MoonParkErrorCode.DATE_IS_NOT_VALID);
        }
    }

    public static int getHours(Date in, Date out) throws MoonParkException {
        checkDateRangeValidation(in, out);

        DateTime inJodaTime = new DateTime(in.getTime());
        DateTime outJodaTime = new DateTime(out.getTime());

        return Hours.hoursBetween(inJodaTime, outJodaTime).getHours();
    }

    public static int getMinutes(Date in, Date out) throws MoonParkException {
        checkDateRangeValidation(in, out);

        DateTime inJodaTime = new DateTime(in.getTime());
        DateTime outJodaTime = new DateTime(out.getTime());

        return Minutes.minutesBetween(inJodaTime, outJodaTime).getMinutes();
    }

    public static int getDays(Date in, Date out) throws MoonParkException {
        checkDateRangeValidation(in, out);

        DateTime inJodaTime = new DateTime(in.getTime());
        DateTime outJodaTime = new DateTime(out.getTime());

        return Days.daysBetween(inJodaTime.withTimeAtStartOfDay(), outJodaTime.plusDays(1).withTimeAtStartOfDay()).getDays();
    }

    public static boolean isWeekday(Date myDate) throws MoonParkException {
        checkDateValidation(myDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        boolean isWeekday = ((dayOfWeek >= Calendar.MONDAY) && (dayOfWeek <= Calendar.FRIDAY));

        return isWeekday;
    }

    public static boolean isWeekend(Date myDate) throws MoonParkException {
        return !isWeekday(myDate);
    }

    public static Map<String, Integer> getHoursMinutesInWeekdayWeekend(Date in, Date out) throws MoonParkException {
        checkDateRangeValidation(in, out);

        Map<String, Integer> mapHoursMinutesInWeekdayWeekend = new HashMap<String, Integer>();
        mapHoursMinutesInWeekdayWeekend.put(WEEKDAY_HOURS, 0);
        mapHoursMinutesInWeekdayWeekend.put(WEEKDAY_MINUTES, 0);
        mapHoursMinutesInWeekdayWeekend.put(WEEKEND_HOURS, 0);
        mapHoursMinutesInWeekdayWeekend.put(WEEKEND_MINUTES, 0);

        int days = getDays(in, out);

        if (days == 1) {
            int hours = getHours(in, out);
            int minutes = getMinutes(in, out);
            if (isWeekend(in)) {
                mapHoursMinutesInWeekdayWeekend.put(WEEKEND_HOURS, hours);
                mapHoursMinutesInWeekdayWeekend.put(WEEKEND_MINUTES, minutes);
            } else {
                mapHoursMinutesInWeekdayWeekend.put(WEEKDAY_HOURS, hours);
                mapHoursMinutesInWeekdayWeekend.put(WEEKDAY_MINUTES, minutes);
            }

            return mapHoursMinutesInWeekdayWeekend;
        }

        Calendar start = Calendar.getInstance();
        start.setTime(in);
        DateTime inJodaTime = new DateTime(in.getTime());

        Calendar end = Calendar.getInstance();
        end.setTime(out);
        DateTime outJodaTime = new DateTime(out.getTime());

        int count = 1;
        int hours = 0;
        int minutes = 0;
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            if (count == 1) {
                Date endOfDateTime = new Date(new DateTime(date.getTime()).plusDays(1).withTimeAtStartOfDay().getMillis());
                hours += getHours(date, endOfDateTime);
                minutes += getMinutes(date, endOfDateTime);
                continue;
            }

            if (count == days) {
                Date startOfDateTime = new Date(new DateTime(date.getTime()).withTimeAtStartOfDay().getMillis());
                hours += getHours(startOfDateTime, date);
                minutes += getMinutes(startOfDateTime, date);
                continue;
            }

            hours += 24;

            if (isWeekday(date)) {
                mapHoursMinutesInWeekdayWeekend.put(WEEKDAY_HOURS, hours);
                mapHoursMinutesInWeekdayWeekend.put(WEEKDAY_MINUTES, minutes);
            } else if (isWeekend(date)) {
                mapHoursMinutesInWeekdayWeekend.put(WEEKEND_HOURS, hours);
                mapHoursMinutesInWeekdayWeekend.put(WEEKEND_MINUTES, minutes);
            } else {
                //NOTHING HERE :)
            }

            count++;
        }

        return mapHoursMinutesInWeekdayWeekend;
    }

    public static Date getEndOfDay(Date date) {
        return DateUtils.addMilliseconds(DateUtils.ceiling(date, Calendar.DATE), -1);
    }

    public static Date getStartOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    public static Map<TimeUnit, Long> computeDiffWithEndOfDate(Date date) throws MoonParkException {
        Date endOfDate = getEndOfDay(date);
        RangeDate rangeDate = new RangeDate(new CheckInDate(date), new CheckOutDate(endOfDate));
        return computeDiff(rangeDate);
    }

    public static Map<TimeUnit, Long> computeDiffWithStartOfDate(Date date) throws MoonParkException {
        Date startOfDate = getStartOfDay(date);
        RangeDate rangeDate = new RangeDate(new CheckInDate(startOfDate), new CheckOutDate(date));
        return computeDiff(rangeDate);
    }

    public static Map<TimeUnit, Long> computeDiff(RangeDate rangeDate) throws MoonParkException {
        long diffInMillies = rangeDate.getCheckOutDate().getDate().getTime() - rangeDate.getCheckInDate().getDate().getTime();
        List<TimeUnit> units = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);
        Map<TimeUnit, Long> result = new LinkedHashMap<TimeUnit, Long>();
        long milliesRest = diffInMillies;
        for (TimeUnit unit : units) {
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit, diff);
        }
        return result;
    }

    public static Map<Date, String> getBetweenDays(Date in, Date out) throws MoonParkException {
        checkDateRangeValidation(in, out);

        if (isSameDay(in, out)) {
            return null;
        }

        Calendar start = Calendar.getInstance();

        start.setTime(in);

        Calendar end = Calendar.getInstance();
        end.setTime(out);

        Map<Date, String> result = new HashMap<Date, String>();
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            if (isSameDay(in, date)) {
                result.put(date, FIRST + "-" + (isWeekday(date) ? WEEKDAY : WEEKEND));
                continue;
            }

            if (isSameDay(out, date)) {
                result.put(date, LAST + "-" + (isWeekday(date) ? WEEKDAY : WEEKEND));
                continue;
            }

            result.put(getStartOfDay(date), isWeekday(date) ? WEEKDAY : WEEKEND);
        }

        return result;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        return DateUtils.isSameDay(date1, date2);
    }

    public static boolean isTimeBetweenTwoTimes(Date startTime, Date stopTime, Date currentTime) throws MoonParkException {
        checkDateRangeValidation(startTime, stopTime);

        checkDateValidation(currentTime);

        if (currentTime.getTime() >= startTime.getTime() && currentTime.getTime() <= stopTime.getTime()) {
            return true;
        }


        return false;
    }

    public List<Date> getListDateBetween(Date in, Date out) throws MoonParkException {
        Map<Date, String> map = getBetweenDays(in, out);
        if (map == null) {
            return null;
        }

        return new ArrayList<Date>(map.keySet());
    }

}
