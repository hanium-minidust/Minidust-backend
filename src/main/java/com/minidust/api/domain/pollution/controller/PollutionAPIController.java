package com.minidust.api.domain.pollution.controller;

import com.minidust.api.domain.pollution.models.PollutionData;
import com.minidust.api.domain.pollution.service.PollutionDataService;
import com.minidust.api.domain.pollution.util.PollutionDataApi;
import com.minidust.api.global.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PollutionAPIController {

    private final PollutionDataApi pollutionDataAPI;
    private final PollutionDataService pollutionDataService;

    @GetMapping("/api/pollution")
    public ResponseEntity<Message> getAllDataBySidoName(@RequestParam String query) {
        if (query.length() != 2) {
            throw new IllegalArgumentException("올바른 주소 이름 형식이 아닙니다. 예시) 서울, 경기");
        }
        List<PollutionData> result = pollutionDataService.getAllByCity(query);
        return ResponseEntity.ok(Message.ok(result));
    }
}
