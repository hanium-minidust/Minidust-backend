package com.minidust.api.service;

import com.minidust.api.models.WeatherData;
import com.minidust.api.util.WeatherAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherAPI weatherAPI;

    public WeatherData getWeatherFromCoords(double longitude, double latitude) {
        return weatherAPI.getWeatherFromCoords(longitude, latitude);
    }
}
