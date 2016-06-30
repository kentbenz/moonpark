package com.restaff.moonpark.model;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "MoonParkPriceResponse")
public class MoonParkPriceResponse {
    private String zone;
    private String checkIn;
    private String checkOut;
    private Float price;

    @XmlElement(name = "zone")
    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @XmlElement(name = "checkIn")
    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    @XmlElement(name = "checkOut")
    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    @XmlElement(name = "price")
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }


}