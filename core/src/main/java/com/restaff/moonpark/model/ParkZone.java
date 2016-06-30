package com.restaff.moonpark.model;

import java.util.Date;

/**
 * Created by DHLE on 6/29/2016.
 */

/**
 * This class define all information of the park
 */
public abstract class ParkZone {
    private String code;
    private String name;
    private String address;

    public ParkZone(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Calculate the price of this park in range time
     * @param rangeDate
     * @return the price of park corresponding with range time
     * @throws MoonParkException
     */
    public abstract Float calculatePrice(RangeDate rangeDate) throws MoonParkException;
}
