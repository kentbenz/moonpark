package com.restaff.moonpark;

import com.restaff.moonpark.model.MoonParkErrorCode;
import com.restaff.moonpark.model.MoonParkException;
import com.restaff.moonpark.util.MoonParkUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by DHLE on 6/29/2016.
 */
public class MoonParkUtilTest {

    @Test
    public void testCheckDateRangeValidation() {
        Date in = null;
        Date out = null;
        try {
            MoonParkUtil.checkDateRangeValidation(in, out);
        } catch (MoonParkException e) {
            Assert.assertEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.NO_CHECKIN_VALUE.getErrorCode());
        }

        in = new Date();
        out = null;
        try {
            MoonParkUtil.checkDateRangeValidation(in, out);
        } catch (MoonParkException e) {
            Assert.assertEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.NO_CHECKOUT_VALUE.getErrorCode());
        }

        in = new Date();
        out = new Date();
        try {
            MoonParkUtil.checkDateRangeValidation(in, out);
        } catch (MoonParkException e) {
            Assert.assertNotEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.NO_CHECKIN_VALUE.getErrorCode());
        }

        in = new Date();
        Calendar calendar = new GregorianCalendar(2013, 1, 28, 13, 24, 56);
        out = calendar.getTime();
        try {
            MoonParkUtil.checkDateRangeValidation(in, out);
        } catch (MoonParkException e) {
            Assert.assertEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.CHECKOUT_BEFORE_CHECKIN.getErrorCode());
        }

        calendar = new GregorianCalendar(2016, 1, 27, 13, 24, 56);
        in = calendar.getTime();
        calendar = new GregorianCalendar(2016, 1, 28, 13, 24, 56);
        out = calendar.getTime();
        try {
            MoonParkUtil.checkDateRangeValidation(in, out);
        } catch (MoonParkException e) {
            Assert.assertNotEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.CHECKOUT_BEFORE_CHECKIN.getErrorCode());
        }
    }

    @Test
    public void testCheckDateValidation() {
        Date date = null;
        try {
            MoonParkUtil.checkDateValidation(date);
        } catch (MoonParkException e) {
            Assert.assertEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.DATE_IS_NOT_VALID.getErrorCode());
        }

        date = new Date();
        try {
            MoonParkUtil.checkDateValidation(date);
        } catch (MoonParkException e) {
            Assert.assertNotEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.DATE_IS_NOT_VALID.getErrorCode());
        }
    }

    @Test
    public void testGetHours() {
        Date in = null;
        Date out = null;
        try {
            MoonParkUtil.getHours(in, out);
        } catch (MoonParkException e) {
            Assert.assertEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.NO_CHECKIN_VALUE.getErrorCode());
        }

        in = new Date();
        out = null;
        try {
            MoonParkUtil.getHours(in, out);
        } catch (MoonParkException e) {
            Assert.assertEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.NO_CHECKOUT_VALUE.getErrorCode());
        }

        in = new Date();
        out = new Date();
        try {
            MoonParkUtil.getHours(in, out);
        } catch (MoonParkException e) {
            Assert.assertNotEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.NO_CHECKIN_VALUE.getErrorCode());
        }

        in = new Date();
        Calendar calendar = new GregorianCalendar(2013, 1, 28, 13, 24, 56);
        out = calendar.getTime();
        try {
            MoonParkUtil.getHours(in, out);
        } catch (MoonParkException e) {
            Assert.assertEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.CHECKOUT_BEFORE_CHECKIN.getErrorCode());
        }

        calendar = new GregorianCalendar(2016, 1, 27, 13, 24, 56);
        in = calendar.getTime();
        calendar = new GregorianCalendar(2016, 1, 28, 13, 24, 56);
        out = calendar.getTime();
        try {
            int hours = MoonParkUtil.getHours(in, out);
            Assert.assertEquals(hours, 24);
        } catch (MoonParkException e) {
            Assert.assertNull(e);
        }

        calendar = new GregorianCalendar(2016, 1, 28, 13, 24, 56);
        in = calendar.getTime();
        calendar = new GregorianCalendar(2016, 1, 28, 17, 24, 56);
        out = calendar.getTime();
        try {
            int hours = MoonParkUtil.getHours(in, out);
            Assert.assertEquals(hours, 4);
        } catch (MoonParkException e) {
            Assert.assertNull(e);
        }

        calendar = new GregorianCalendar(2016, 1, 28, 17, 24, 55);
        in = calendar.getTime();
        calendar = new GregorianCalendar(2016, 1, 28, 17, 24, 56);
        out = calendar.getTime();
        try {
            int hours = MoonParkUtil.getHours(in, out);
            Assert.assertEquals(hours, 0);
        } catch (MoonParkException e) {
            Assert.assertNull(e);
        }

        calendar = new GregorianCalendar(2016, 1, 28, 17, 24, 50);
        in = calendar.getTime();
        calendar = new GregorianCalendar(2016, 1, 28, 17, 24, 56);
        out = calendar.getTime();
        try {
            MoonParkUtil.getHours(in, out);
        } catch (MoonParkException e) {
            Assert.assertNotNull(e);
            Assert.assertEquals(e.getMoonParkErrorCode().getErrorCode(), MoonParkErrorCode.CHECKOUT_BEFORE_CHECKIN.getErrorCode());
        }
    }

    @Test
    public void testGetDays() {
        Calendar calendar = new GregorianCalendar(2016, 1, 25, 01, 24, 50);
        Date in = calendar.getTime();

        calendar = new GregorianCalendar(2016, 1, 28, 01, 24, 50);
        Date out = calendar.getTime();

        try {
            int days = MoonParkUtil.getDays(in, out);
            //Assert.assertEquals(days, 3);
            System.out.println(days);
        } catch (MoonParkException e) {
            Assert.assertNull(e);
        }
    }

    @Test
    public void testGetHoursMinutesInWeekdayWeekend() {
        Calendar calendar = new GregorianCalendar(2016, 1, 26, 11, 24, 50);
        Date in = calendar.getTime();

        calendar = new GregorianCalendar(2016, 1, 29, 17, 40, 50);
        Date out = calendar.getTime();

        try {
            Map<String, Integer> map = MoonParkUtil.getHoursMinutesInWeekdayWeekend(in, out);
            Assert.assertNotNull(map);
        } catch (MoonParkException e) {
            Assert.assertNull(e);
        }
    }

    /*@Test
    public void testComputeDiff() {
        Calendar calendar = new GregorianCalendar(2016,1,26,11,00,00);
        Date in = calendar.getTime();

        calendar = new GregorianCalendar(2016,1,29,17,59,59);
        Date out = calendar.getTime();

        try {
            Map<String, Long> map = MoonParkUtil.computeDiff(in, out);
            System.out.println(map);
        } catch (MoonParkException e) {
            Assert.assertNull(e);
        }
    }*/
}
