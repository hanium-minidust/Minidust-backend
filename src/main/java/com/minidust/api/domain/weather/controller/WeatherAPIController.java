package com.minidust.api.domain.weather.controller;

import com.minidust.api.domain.weather.dto.WeatherDataDto;
import com.minidust.api.domain.weather.service.WeatherService;
import com.minidust.api.global.response.Message;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(Message.ok(weatherDataDto));
    }
}
