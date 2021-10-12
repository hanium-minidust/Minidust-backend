package com.minidust.api.domain.map.controller;

import com.minidust.api.domain.map.dto.AddrToCoordsDto;
import com.minidust.api.domain.map.dto.CoordsToAddrDto;
import com.minidust.api.domain.map.service.MapService;
import com.minidust.api.global.response.Message;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Validated
@RestController
public class MapController {

    @GetMapping("/api/map/addressToCoords")
    public ResponseEntity<?> getCoordsFromAddress(@RequestParam @Length(min = 1, max = 3) @NotNull String query) {
        AddrToCoordsDto result = MapService.getCoordsFromAddress(query);
        return ResponseEntity.ok(Message.ok(result));
    }

    @GetMapping("/api/map/coordsToAddress")
    public ResponseEntity<?> getAddressFromCoordinates(@RequestParam @DecimalMin("123") @DecimalMax("133") double lon,
                                                       @RequestParam @DecimalMin("32") @DecimalMax("44") double lat) {
        CoordsToAddrDto result = MapService.getAddressFromCoordinates(lon, lat);
        return ResponseEntity.ok(Message.ok(result));
    }
}
