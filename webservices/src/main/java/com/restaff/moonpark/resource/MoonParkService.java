package com.restaff.moonpark.resource;

import com.restaff.moonpark.exception.BadRequestException;
import io.swagger.annotations.*;
import com.restaff.moonpark.data.MoonParkData;
import com.restaff.moonpark.exception.ApiException;
import com.restaff.moonpark.exception.NotFoundException;
import com.restaff.moonpark.model.MoonParkPriceResponse;
import com.restaff.moonpark.model.ParkZone;
import com.restaff.moonpark.model.RangeDate;
import com.restaff.moonpark.model.CheckInDate;
import com.restaff.moonpark.model.CheckOutDate;
import com.restaff.moonpark.model.MoonParkException;


import javax.ws.rs.core.Response;
import javax.ws.rs.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Path("/moonpark")
@Api(value = "/price", description = "Calculate price...")
@Produces({"application/json"})
public class MoonParkService {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    static MoonParkData moonParkData = new MoonParkData();

    @GET
    @Path("/price")
    @ApiOperation(value = "Calculate price by zone, check in date and check out date",
            response = MoonParkPriceResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Invalid zone supplied"),
            @ApiResponse(code = 401, message = "Check in date time not valid"),
            @ApiResponse(code = 402, message = "Check out date time not valid"),
            @ApiResponse(code = 403, message = "Check out date time does not allow before check in date time"),
            @ApiResponse(code = 405, message = "Sorry, We can not get price at the moment. Please try later")
    })
    public Response getPrice(
            @ApiParam(value = "Choose the zone for get price", required = true, defaultValue = "M1", allowableValues = "M1,M2,M3", allowMultiple = false) @QueryParam("zone") String zone,
            @ApiParam(value = "Input the check in date time. Use pattern dd/MM/yyyy HH:mm (Ex: 28/06/2016 09:16)", required = true) @QueryParam("checkIn") String checkIn,
            @ApiParam(value = "Input the check out date time. Use pattern dd/MM/yyyy HH:mm (Ex: 28/06/2016 09:16)", required = true) @QueryParam("checkOut") String checkOut)
            throws ApiException {
        ParkZone parkZone = moonParkData.findParkZone(zone);
        if (parkZone == null) {
            throw new NotFoundException(404, "Zone {" + zone + "} not found");
        }

        Date checkInDate = null;
        try {
            checkInDate = sdf.parse(checkIn);
        } catch (ParseException e) {
            throw new BadRequestException(401, "Check in date time not valid");
        }

        Date checkOutDate = null;
        try {
            checkOutDate = sdf.parse(checkOut);
        } catch (ParseException e) {
            throw new BadRequestException(401, "Check out date time not valid");
        }

        if (checkOutDate.before(checkInDate)) {
            throw new BadRequestException(401, "Check out date time does not allow before check in date time");
        }

        RangeDate rangeDate = new RangeDate(new CheckInDate(checkInDate), new CheckOutDate(checkOutDate));

        MoonParkPriceResponse moonParkPriceResponse = new MoonParkPriceResponse();
        moonParkPriceResponse.setZone(zone);
        moonParkPriceResponse.setCheckIn(checkIn);
        moonParkPriceResponse.setCheckOut(checkOut);
        try {
            moonParkPriceResponse.setPrice(parkZone.calculatePrice(rangeDate));
        } catch (MoonParkException e) {
            throw new NotFoundException(405, "Sorry, We can not get price at the moment. Please try later");
        }
        return Response.ok().entity(moonParkPriceResponse).build();
    }
}
