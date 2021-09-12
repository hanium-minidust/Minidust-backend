package com.minidust.api.controller;

import com.minidust.api.dto.WeatherDataDto;
import com.minidust.api.models.Message;
import com.minidust.api.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

@Validated
@RequiredArgsConstructor
@RestController
public class WeatherAPIController {
    private final WeatherService weatherService;

    @GetMapping("/api/weather")
    public ResponseEntity<?> getWeatherFromCoords(@RequestParam @DecimalMin("123") @DecimalMax("133") double lon,
                                                  @RequestParam @DecimalMin("32") @DecimalMax("44") double lat) {
        WeatherDataDto weatherDataDto = weatherService.getWeatherFromCoords(lon, lat);
        return new ResponseEntity<>(Message.getDefaultOkMessage(weatherDataDto), HttpStatus.OK);
    }
}
