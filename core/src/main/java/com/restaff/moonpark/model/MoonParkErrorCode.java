package com.restaff.moonpark.model;

/**
 * Created by DHLE on 6/29/2016.
 */
public enum MoonParkErrorCode {
    NO_CHECKIN_VALUE(100, "Check-in date is not found"),
    NO_CHECKOUT_VALUE(200, "Check-out date is not found"),
    CHECKOUT_BEFORE_CHECKIN(300, "Check-out date doesn't allow before check-in date"),
    DATE_IS_NOT_VALID(400, "Date is not valid");

    private int errorCode;
    private String errorMessage;

    MoonParkErrorCode(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
