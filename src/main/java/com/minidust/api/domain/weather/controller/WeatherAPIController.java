package com.minidust.api.domain.weather.controller;

import com.minidust.api.domain.map.service.MapService;
import com.minidust.api.domain.weather.dto.WeatherDataDto;
import com.minidust.api.domain.weather.service.WeatherAPI;
import com.minidust.api.global.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class WeatherAPIController {
    private final WeatherAPI weatherAPI;

    @GetMapping("/weather")
    public ResponseEntity<Message> getWeatherFromCoords(@RequestParam double lon, @RequestParam double lat) {
        if (!MapService.isCorrectCoords(lon, lat)) {
            throw new IllegalArgumentException("좌표의 범위가 한국 범위를 벗어납니다.");
        }
        WeatherDataDto weatherDataDto = weatherAPI.getWeatherFromCoords(lon, lat);
        return ResponseEntity.ok(Message.ok(weatherDataDto));
    }
}
