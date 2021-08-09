package com.minidust.api.controller;

import com.minidust.api.util.CoordinatesToAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapController {

    private final CoordinatesToAddress coordinatesToAddress;

    @Autowired
    public MapController(CoordinatesToAddress coordinatesToAddress) {
        this.coordinatesToAddress = coordinatesToAddress;
    }

    @GetMapping("/api/map/addressToCoords")
    public void getFromAddress(@RequestParam String query) {
        coordinatesToAddress.getCoordinatesFromAddress(query);
    }

    @GetMapping("/api/map/coordsToAddress")
    public void getAddressFromCoordinates(@RequestParam String longitude, @RequestParam String latitude) {
        Double passedLongitude = Double.valueOf(longitude);
        Double passedLatitude = Double.valueOf(latitude);
        coordinatesToAddress.getAddressFromCoordinates(passedLongitude, passedLatitude);
    }
}
