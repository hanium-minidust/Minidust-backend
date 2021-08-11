package com.minidust.api.controller;

import com.minidust.api.models.Message;
import com.minidust.api.util.CoordinatesToAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Validated
@RestController
public class MapController {

    private final CoordinatesToAddress coordinatesToAddress;

    @Autowired
    public MapController(CoordinatesToAddress coordinatesToAddress) {
        this.coordinatesToAddress = coordinatesToAddress;
    }

    @GetMapping("/api/map/addressToCoords")
    public ResponseEntity<?> getCoordsFromAddress(@RequestParam @NotNull String query) {
        HashMap<String, Double> coordsResult = new HashMap<>();
        List<Double> result = coordinatesToAddress.getCoordsFromAddress(query);
        coordsResult.put("longitude", result.get(0));
        coordsResult.put("latitude", result.get(1));

        return new ResponseEntity<>(Message.getDefaultOkMessage(coordsResult), HttpStatus.OK);
    }

    @GetMapping("/api/map/coordsToAddress")
    public ResponseEntity<?> getAddressFromCoordinates(@RequestParam @DecimalMin("123") @DecimalMax("133") double lon,
                                                       @RequestParam @DecimalMin("32") @DecimalMax("44") double lat) {
        List<String> addResult = coordinatesToAddress.getAddressFromCoordinates(lon, lat);
        LinkedHashMap<String, String> addressResult = new LinkedHashMap<>();
        addressResult.put("first", addResult.get(0));
        addressResult.put("alias", addResult.get(1));
        addressResult.put("second", addResult.get(2));
        addressResult.put("third", addResult.get(3));

        return new ResponseEntity<>(Message.getDefaultOkMessage(addressResult), HttpStatus.OK);
    }
}
