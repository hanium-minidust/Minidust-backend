package com.minidust.api.controller;

import com.minidust.api.dto.AddrToCoordsDto;
import com.minidust.api.dto.CoordsToAddrDto;
import com.minidust.api.models.Message;
import com.minidust.api.util.BiCoordsToAddr;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
public class MapController {

    @GetMapping("/api/map/addressToCoords")
    public ResponseEntity<?> getCoordsFromAddress(@RequestParam @Length(min = 1, max = 3) @NotNull String query) {
        List<Double> result = BiCoordsToAddr.getCoordsFromAddress(query);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("올바른 지역 주소 형식이 아닙니다. 예시) 서울시, 성남시, 안성시, 당왕동 등");
        }
        AddrToCoordsDto addrToCoordsDto = new AddrToCoordsDto(result.get(0), result.get(1));
        return new ResponseEntity<>(Message.getDefaultOkMessage(addrToCoordsDto), HttpStatus.OK);
    }

    @GetMapping("/api/map/coordsToAddress")
    public ResponseEntity<?> getAddressFromCoordinates(@RequestParam @DecimalMin("123") @DecimalMax("133") double lon,
                                                       @RequestParam @DecimalMin("32") @DecimalMax("44") double lat) {
        List<String> addResult = BiCoordsToAddr.getAddressFromCoordinates(lon, lat);
        CoordsToAddrDto coordsToAddrDto = new CoordsToAddrDto(addResult.get(0), addResult.get(1), addResult.get(2), addResult.get(3));
        return new ResponseEntity<>(Message.getDefaultOkMessage(coordsToAddrDto), HttpStatus.OK);
    }
}
