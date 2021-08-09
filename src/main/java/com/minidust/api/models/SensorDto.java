package com.minidust.api.models;

import lombok.Getter;

@Getter
public class SensorDto {
    private int id;
    private double longitude;
    private double latitude;
    private double temperature;
    private double humidity;
    private int pm25;
    private int pm10;
}
