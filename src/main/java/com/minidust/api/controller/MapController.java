package com.minidust.api.controller;

import com.minidust.api.util.CoordinatesToAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class MapController {

    private final CoordinatesToAddress coordinatesToAddress;

    @Autowired
    public MapController(CoordinatesToAddress coordinatesToAddress) {
        this.coordinatesToAddress = coordinatesToAddress;
    }

    @GetMapping("/api/map/addressToCoords")
    public HashMap<String, Double> getFromAddress(@RequestParam String query) {
        HashMap<String, Double> coordsResult = new HashMap<>();

        double longitude = coordinatesToAddress.getCoordinatesFromAddress(query).get(0);
        double latitude = coordinatesToAddress.getCoordinatesFromAddress(query).get(1);
        coordsResult.put("longitude", longitude);
        coordsResult.put("latitude", latitude);
        return coordsResult;
    }

    @GetMapping("/api/map/coordsToAddress")
    public LinkedHashMap<String, String> getAddressFromCoordinates(@RequestParam String lon, @RequestParam String lat) {
        Double passedLongitude = Double.valueOf(lon);
        Double passedLatitude = Double.valueOf(lat);
        List<String> result = coordinatesToAddress.getAddressFromCoordinates(passedLongitude, passedLatitude);
        LinkedHashMap<String, String> addressResult = new LinkedHashMap<>();
        addressResult.put("First", result.get(0));
        addressResult.put("FirstAlias", result.get(1));
        addressResult.put("Second", result.get(2));
        addressResult.put("Third", result.get(3));
        return addressResult;
    }
}
