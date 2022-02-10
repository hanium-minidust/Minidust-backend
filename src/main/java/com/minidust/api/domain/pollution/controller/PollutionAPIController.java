package com.minidust.api.domain.pollution.controller;

import com.minidust.api.domain.pollution.models.Pollution;
import com.minidust.api.domain.pollution.service.PollutionDataService;
import com.minidust.api.domain.pollution.service.PollutionUpdateService;
import com.minidust.api.global.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/pollution")
@RequiredArgsConstructor
@RestController
public class PollutionAPIController {

    private final PollutionUpdateService pollutionUpdateService;
    private final PollutionDataService pollutionDataService;

    @GetMapping
    public ResponseEntity<Message> findByCityName(@RequestParam String cityName) {
        if (cityName.length() != 2) {
            throw new IllegalArgumentException("올바른 주소 이름 형식이 아닙니다. 예시) 서울, 경기");
        }
        List<Pollution> result = pollutionDataService.findByCityName(cityName);
        return ResponseEntity.ok(Message.ok(result));
    }

    @GetMapping("/update/data")
    public ResponseEntity<Message> updateDust(@RequestParam String sidoName) {
        pollutionUpdateService.updatePollutionData(sidoName);
        return ResponseEntity.ok(Message.ok());
    }

    @GetMapping("/update/station")
    public ResponseEntity<Message> updateStation(@RequestParam String sidoName) {
        pollutionUpdateService.updatePollutionStation(sidoName);
        return ResponseEntity.ok(Message.ok());
    }
}
