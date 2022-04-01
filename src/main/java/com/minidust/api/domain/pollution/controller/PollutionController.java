package com.minidust.api.domain.pollution.controller;

import com.minidust.api.domain.pollution.models.Pollution;
import com.minidust.api.domain.pollution.service.PollutionService;
import com.minidust.api.domain.pollution.service.StationService;
import com.minidust.api.global.response.Message;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/pollution")
@RequiredArgsConstructor
@RestController
public class PollutionController {

    private final StationService stationService;
    private final PollutionService pollutionService;

    @GetMapping
    @ApiOperation(value = "미세먼지 정보 조회", notes = "도시 이름을 받아 해당 도시 미세먼지 정보를 조회합니다.")
    public ResponseEntity<Message> findByCityName(@RequestParam("query") String cityName) {
        if (cityName.length() != 2) {
            throw new IllegalArgumentException("올바른 주소 이름 형식이 아닙니다. 예시) 서울, 경기");
        }
        List<PollutionDto> result = pollutionService.findAllByName(cityName).stream().map(PollutionDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(Message.ok(result));
    }

    @GetMapping("/update/data")
    @ApiOperation(value = "미세먼지 정보 업데이트", notes = "미세먼지 정보 업데이트를 위한 컨트롤러 입니다.")
    public ResponseEntity<Message> updateDust(@RequestParam("query") String sidoName) {
        pollutionService.updateDust(sidoName);
        return ResponseEntity.ok(Message.ok());
    }

    @GetMapping("/update/station")
    @ApiOperation(value = "미세먼지 측정소 정보 업데이트", notes = "미세먼지 측정소 정보 업데이트를 위한 컨트롤러 입니다.")
    public ResponseEntity<Message> updateStation(@RequestParam("query") String sidoName) {
        stationService.updateStation(sidoName);
        return ResponseEntity.ok(Message.ok());
    }

    @Data
    static class PollutionDto {
        private String stationName;
        private Double longitude;
        private Double latitude;
        private int pm25;
        private int pm10;

        public PollutionDto(Pollution pollution) {
            this.stationName = pollution.getStationName();
            this.longitude = pollution.getLongitude();
            this.latitude = pollution.getLatitude();
            this.pm10 = pollution.getPm10();
            this.pm25 = pollution.getPm25();
        }
    }
}
