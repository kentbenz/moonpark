package com.restaff.moonpark.model;


import java.util.List;

/**
 * Created by DHLE on 6/29/2016.
 */
public class ParkZone3 extends ParkZone {

    static final Float PRICE_PER_MINUTE_IN_8_16 = 2F;
    static final Float PRICE_PER_MINUTE_IN_8_16_FOR_FIRST_HOUR = 0F;
    static final Float PRICE_PER_MINUTE_OUT_8_16 = 3F;
    static final Float PRICE_PER_MINUTE_FOR_SUNDAY = 0F;

    static final MoonParkTime EIGHT_OCLOCK = new MoonParkTime(8, 0);//08:00AM
    static final MoonParkTime NINE_OCLOCK = new MoonParkTime(9, 0);//09:00AM
    static final MoonParkTime SIXTEEN_OCLOCK = new MoonParkTime(16, 0);//16:00PM
    static final MoonParkTime BEGIN_DATE_OCLOCK = new MoonParkTime(0, 0);//00:00AM
    static final MoonParkTime END_DATE_OCLOCK = new MoonParkTime(23, 59);//23:56PM

    public ParkZone3(String code) {
        super(code);
    }

    public Float calculatePrice(RangeDate rangeDate) throws MoonParkException {
        if (rangeDate.isCheckOutSameCheckInDate()) {
            CheckInDate checkInDate = rangeDate.getCheckInDate();
            if (checkInDate.isSunday()) {
                return PRICE_PER_MINUTE_FOR_SUNDAY;
            }

            CheckOutDate checkOutDate = rangeDate.getCheckOutDate();

            MoonParkTime startTime = checkInDate.getMoonParkTime();
            MoonParkTime endTime = checkOutDate.getMoonParkTime();

            return getPrice(new RangeTime(startTime, endTime));
        }


        CheckInDate checkInDate = rangeDate.getCheckInDate();
        Float totalPriceInCheckInDay = 0F;
        if (checkInDate.isSunday()) {
            totalPriceInCheckInDay = PRICE_PER_MINUTE_FOR_SUNDAY;
        } else {
            totalPriceInCheckInDay = getPrice(new RangeTime(checkInDate.getMoonParkTime(), END_DATE_OCLOCK));
        }

        CheckOutDate checkOutDate = rangeDate.getCheckOutDate();
        Float totalPriceInCheckOutDay = 0F;
        if (checkOutDate.isSunday()) {
            totalPriceInCheckOutDay = PRICE_PER_MINUTE_FOR_SUNDAY;
        } else {
            totalPriceInCheckOutDay = getPrice(new RangeTime(BEGIN_DATE_OCLOCK, checkOutDate.getMoonParkTime()));
        }

        Float totalPriceInStayDays = 0F;
        List<StayInParkDate> stayInParkDateList = rangeDate.getStayInParkExclusiveInOut();
        if (stayInParkDateList == null || stayInParkDateList.isEmpty()) {
            totalPriceInStayDays = 0F;
        } else {
            for (StayInParkDate stayInParkDate : stayInParkDateList) {
                totalPriceInStayDays += getPrice(new RangeTime(BEGIN_DATE_OCLOCK, END_DATE_OCLOCK));
            }
        }

        return totalPriceInCheckInDay + totalPriceInStayDays + totalPriceInCheckOutDay;
    }

    Float getPrice(RangeTime rangeTime) {
        int checkInMinute = rangeTime.getStartTime().getTotalMinutes();
        int checkOutMinute = rangeTime.getEndTime().getTotalMinutes();

        //case 1 and case 5
        //0------in---out----8----9---------------16---in----out------23
        if ((checkInMinute >= BEGIN_DATE_OCLOCK.getTotalMinutes() && checkOutMinute <= EIGHT_OCLOCK.getTotalMinutes()) ||
                (checkInMinute >= SIXTEEN_OCLOCK.getTotalMinutes() && checkOutMinute <= END_DATE_OCLOCK.getTotalMinutes())) {
            return PRICE_PER_MINUTE_OUT_8_16 * rangeTime.getTotalMinutes();
        }

        //case 2 and 2.1
        if ( (checkInMinute >= BEGIN_DATE_OCLOCK.getTotalMinutes() && checkInMinute <= EIGHT_OCLOCK.getTotalMinutes()) &&
                (checkOutMinute >= EIGHT_OCLOCK.getTotalMinutes() && checkOutMinute <= SIXTEEN_OCLOCK.getTotalMinutes())) {
            //0--------in-----8--out--9--------------16-------------23
            if (checkOutMinute >= EIGHT_OCLOCK.getTotalMinutes() && checkOutMinute <= NINE_OCLOCK.getTotalMinutes()) {
                return PRICE_PER_MINUTE_OUT_8_16 * rangeTime.getTotalMinutesTo(EIGHT_OCLOCK) +//IN->8:00
                        PRICE_PER_MINUTE_IN_8_16_FOR_FIRST_HOUR;//8:00->out (free)
            }

            //0--------in-----8----9-----out---------16-------------23
            return PRICE_PER_MINUTE_OUT_8_16 * rangeTime.getTotalMinutesTo(EIGHT_OCLOCK) + //IN->8:00
                    PRICE_PER_MINUTE_IN_8_16_FOR_FIRST_HOUR +//8:00->9:00 (free)
                    PRICE_PER_MINUTE_IN_8_16 * rangeTime.getTotalMinutesFrom(NINE_OCLOCK);//9:00->OUT
        }

        //case 3
        if (checkInMinute >= EIGHT_OCLOCK.getTotalMinutes() && checkOutMinute <= SIXTEEN_OCLOCK.getTotalMinutes()) {
            //0-------------8--in----out--9--------------16-------------23
            if (checkOutMinute >= EIGHT_OCLOCK.getTotalMinutes() && checkOutMinute <= NINE_OCLOCK.getTotalMinutes()) {
                return PRICE_PER_MINUTE_IN_8_16_FOR_FIRST_HOUR;//free
            }

            //0-----------8--in--9------out--------16-------------23
            if (checkInMinute >= EIGHT_OCLOCK.getTotalMinutes() && checkInMinute <= NINE_OCLOCK.getTotalMinutes()) {
                return PRICE_PER_MINUTE_IN_8_16_FOR_FIRST_HOUR +//IN->9:00 (free)
                        PRICE_PER_MINUTE_OUT_8_16 * rangeTime.getTotalMinutesFrom(NINE_OCLOCK);//9:00->OUT
            }
            //0------------8----9--in-------out-----16-------------23
            return PRICE_PER_MINUTE_IN_8_16 + rangeTime.getTotalMinutes();//In->OUT
        }

        //case 4
        if ( (checkInMinute >= EIGHT_OCLOCK.getTotalMinutes() && checkInMinute <= SIXTEEN_OCLOCK.getTotalMinutes()) &&
                (checkOutMinute >= SIXTEEN_OCLOCK.getTotalMinutes() && checkOutMinute <= END_DATE_OCLOCK.getTotalMinutes())) {
            //0-------------8--in--9--------------16------out-------23
            if (checkInMinute >= EIGHT_OCLOCK.getTotalMinutes() && checkInMinute <= NINE_OCLOCK.getTotalMinutes()) {
                return PRICE_PER_MINUTE_IN_8_16_FOR_FIRST_HOUR +//IN->9:00 (free)
                        PRICE_PER_MINUTE_IN_8_16 * rangeTime.getTotalMinutes(NINE_OCLOCK, SIXTEEN_OCLOCK) +//9:00->16:00
                        PRICE_PER_MINUTE_OUT_8_16 * rangeTime.getTotalMinutesFrom(SIXTEEN_OCLOCK);//16:00->OUT
            }

            //0-------------8----9---in-----------16------out-------23
            return PRICE_PER_MINUTE_IN_8_16 * rangeTime.getTotalMinutesTo(SIXTEEN_OCLOCK) +//IN->16:00
                    PRICE_PER_MINUTE_OUT_8_16 * rangeTime.getTotalMinutesFrom(SIXTEEN_OCLOCK);//16:00->OUT
        }

        //case 6
        //0--------in-----8----9--------------16------out-------23
        if ( (checkInMinute >= BEGIN_DATE_OCLOCK.getTotalMinutes() && checkInMinute <= EIGHT_OCLOCK.getTotalMinutes()) &&
                (checkOutMinute >= SIXTEEN_OCLOCK.getTotalMinutes() && checkOutMinute <= END_DATE_OCLOCK.getTotalMinutes())) {
            return PRICE_PER_MINUTE_OUT_8_16 * rangeTime.getTotalMinutesTo(EIGHT_OCLOCK) + //IN->8:00
                    PRICE_PER_MINUTE_IN_8_16_FOR_FIRST_HOUR +//8:00->9:00
                    PRICE_PER_MINUTE_IN_8_16 * rangeTime.getTotalMinutes(NINE_OCLOCK, SIXTEEN_OCLOCK) +//9:00->16:00
                    PRICE_PER_MINUTE_OUT_8_16 * rangeTime.getTotalMinutesFrom(SIXTEEN_OCLOCK);//16:00->OUT
        }

        return 0F;
    }
}
