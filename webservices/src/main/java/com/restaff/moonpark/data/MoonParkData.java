package com.restaff.moonpark.data;

import com.restaff.moonpark.model.ParkZone;
import com.restaff.moonpark.model.ParkZone1;
import com.restaff.moonpark.model.ParkZone2;
import com.restaff.moonpark.model.ParkZone3;

import java.util.List;
import java.util.ArrayList;

public class MoonParkData {
    static List<ParkZone> parkZones = new ArrayList<ParkZone>();

    static {
        parkZones.add(new ParkZone1("M1"));
        parkZones.add(new ParkZone2("M2"));
        parkZones.add(new ParkZone3("M3"));
    }

    public ParkZone findParkZone(String zoneCode) {
        for (ParkZone parkZone : parkZones) {
            if (parkZone.getCode().equals(zoneCode)) {
                return parkZone;
            }
        }
        return null;
    }
}