package com.minidust.api.domain.map.controller;

import com.minidust.api.domain.map.dto.AddrToCoordsDto;
import com.minidust.api.domain.map.dto.CoordsToAddrDto;
import com.minidust.api.domain.map.service.MapService;
import com.minidust.api.global.response.Message;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "명칭을 좌표로 변환", notes = "주소의 구 혹은 동을 받아 위도와 경도로 반환합니다.")
    public ResponseEntity<Message> getCoordsFromAddress(@RequestParam String query) {
        AddrToCoordsDto result = MapService.getCoordsFromAddress(query);
        return ResponseEntity.ok(Message.ok(result));
    }

    @GetMapping("/coordsToAddress")
    @ApiOperation(value = "좌표를 명칭으로 반환", notes = "좌표를 받아 해당 좌표의 주소를 반환합니다.")
    public ResponseEntity<Message> getAddressFromCoordinates(@RequestParam double lon, @RequestParam double lat) {
        CoordsToAddrDto result = MapService.getAddressFromCoordinates(lon, lat);
        return ResponseEntity.ok(Message.ok(result));
    }
}
