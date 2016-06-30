package com.restaff.moonpark.model;

/**
 * Created by DHLE on 6/29/2016.
 */
public class MoonParkException extends Exception {
    private MoonParkErrorCode moonParkErrorCode;

    public MoonParkException(MoonParkErrorCode moonParkErrorCode) {
        super();
        this.moonParkErrorCode = moonParkErrorCode;
    }

    public MoonParkErrorCode getMoonParkErrorCode() {
        return moonParkErrorCode;
    }

    public void setMoonParkErrorCode(MoonParkErrorCode moonParkErrorCode) {
        this.moonParkErrorCode = moonParkErrorCode;
    }
}
