package com.minidust.api.domain.weather.service;

import com.minidust.api.domain.weather.dto.WeatherDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherAPI weatherAPI;

    public WeatherDataDto getWeatherFromCoords(double longitude, double latitude) {
        return weatherAPI.getWeatherFromCoords(longitude, latitude);
    }
}
