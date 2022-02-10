package com.minidust.api.domain.map.controller;

import com.minidust.api.domain.map.dto.AddrToCoordsDto;
import com.minidust.api.domain.map.dto.CoordsToAddrDto;
import com.minidust.api.domain.map.service.MapService;
import com.minidust.api.global.response.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/map")
public class MapController {

    @GetMapping("/addressToCoords")
    public ResponseEntity<Message> getCoordsFromAddress(@RequestParam String query) {
        AddrToCoordsDto result = MapService.getCoordsFromAddress(query);
        return ResponseEntity.ok(Message.ok(result));
    }

    @GetMapping("/coordsToAddress")
    public ResponseEntity<Message> getAddressFromCoordinates(@RequestParam double lon, @RequestParam double lat) {
        CoordsToAddrDto result = MapService.getAddressFromCoordinates(lon, lat);
        return ResponseEntity.ok(Message.ok(result));
    }
}
