package com.minidust.api.controller;

import com.minidust.api.models.Message;
import com.minidust.api.models.PollutionData;
import com.minidust.api.service.PollutionDataService;
import com.minidust.api.util.PollutionApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PollutionAPIController {

    private final PollutionApi pollutionAPI;
    private final PollutionDataService pollutionDataService;

    @GetMapping("/api/pollution")
    public ResponseEntity<?> getAllDataBySidoName(@RequestParam String query) {
        if (query.length() != 2) {
            throw new IllegalArgumentException("올바른 주소 이름 형식이 아닙니다. 예시) 서울, 경기, 전북, 충남 등");
        }
        List<PollutionData> result = pollutionDataService.getAllByCity(query);
        return new ResponseEntity<>(Message.getDefaultOkMessage(result), HttpStatus.OK);
    }


    @GetMapping("/api/pollution/update")
    public void updatePollutionData(@RequestParam String query) {
        pollutionAPI.updatePollutionData(query);
    }
}
