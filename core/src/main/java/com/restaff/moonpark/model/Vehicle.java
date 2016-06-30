package com.restaff.moonpark.model;

/**
 * Created by DHLE on 6/29/2016.
 */
public class Vehicle {
    private String palteNumber;

    public Vehicle(String palteNumber) {
        this.palteNumber = palteNumber;
    }

    public String getPalteNumber() {
        return palteNumber;
    }

    public void setPalteNumber(String palteNumber) {
        this.palteNumber = palteNumber;
    }
}
