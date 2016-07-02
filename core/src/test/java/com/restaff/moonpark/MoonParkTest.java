package com.restaff.moonpark;

import com.restaff.moonpark.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by DHLE on 7/3/2016.
 */
public class MoonParkTest {

    @Test
    public void testPrice() {
        File testFile = new File("test_data.txt") ;
        try {
            System.setIn(new FileInputStream(testFile));

            Scanner scanner = new Scanner(System.in);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.startsWith("#")) {
                    continue;
                }

                StringTokenizer token = new StringTokenizer(line, "|");

                String zoneCode = token.nextToken();
                Date checkInDate = sdf.parse(token.nextToken());
                Date checkOutDate = sdf.parse(token.nextToken());
                Float expected = Float.parseFloat(token.nextToken());

                RangeDate rangeDate = new RangeDate(new CheckInDate(checkInDate), new CheckOutDate(checkOutDate));

                if ("M1".equalsIgnoreCase(zoneCode)) {
                    Assert.assertEquals(new ParkZone1(zoneCode).calculatePrice(rangeDate), expected);
                }

                if ("M2".equalsIgnoreCase(zoneCode)) {
                    Assert.assertEquals(new ParkZone2(zoneCode).calculatePrice(rangeDate), expected);
                }

                if ("M3".equalsIgnoreCase(zoneCode)) {
                    Assert.assertEquals(new ParkZone3(zoneCode).calculatePrice(rangeDate), expected);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (MoonParkException e) {
            e.printStackTrace();
        }
    }
}
