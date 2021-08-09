package com.minidust.api.controller;

import com.minidust.api.models.WeatherData;
import com.minidust.api.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class WeatherAPIController {
    private final WeatherService weatherService;

    @GetMapping("/api/weather")
    public WeatherData getWeatherFromCoords(@RequestParam double lon, @RequestParam double lat) {
        return weatherService.getWeatherFromCoords(lon, lat);
    }
}
