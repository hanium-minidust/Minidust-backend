package com.minidust.api.controller;

import com.minidust.api.models.Message;
import com.minidust.api.models.PollutionData;
import com.minidust.api.service.PollutionDataService;
import com.minidust.api.util.PollutionAPI;
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

    private final PollutionAPI pollutionAPI;
    private final PollutionDataService pollutionDataService;

    @GetMapping("/api/pollution")
    public ResponseEntity<?> getAllDataBySidoName(@RequestParam String query) {
        if (query.length() < 2) {
            return new ResponseEntity<>(Message.getDefaultBadRequestMessage(), HttpStatus.BAD_REQUEST);
        }
        List<PollutionData> result = pollutionDataService.getAllByCity(query);
        return new ResponseEntity<>(Message.getDefaultOkMessage(result), HttpStatus.OK);
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
