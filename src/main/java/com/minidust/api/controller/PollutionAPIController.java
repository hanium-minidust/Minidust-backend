package com.minidust.api.controller;

import com.minidust.api.models.Message;
import com.minidust.api.models.StatusEnum;
import com.minidust.api.service.PollutionDataService;
import com.minidust.api.util.PollutionAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class PollutionAPIController {

    private final PollutionAPI pollutionAPI;
    private final PollutionDataService pollutionDataService;

    @GetMapping("/api/pollution")
    public ResponseEntity<?> getAllDataBySidoName(@RequestParam @Valid String query, BindingResult result) {
        // TODO 이건 오류가 생길일이 없나? 그리고 String query에서 query 의 유효성?
        Message message;
        message = new Message(StatusEnum.OK, "OK", pollutionDataService.getAllByCity(query));
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/api/pollution/update-data")
    public void updatePollutionData(@RequestParam String query) {
        pollutionAPI.updatePollutionData(query);
    }

    @GetMapping("/api/pollution/update-station")
    public void updatePollutionStation(@RequestParam String query) {
        pollutionAPI.updatePollutionData(query);
    }
}
