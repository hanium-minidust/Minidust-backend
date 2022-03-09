package com.minidust.api.domain.sensor.dto;

import com.minidust.api.domain.sensor.models.Sensor;
import lombok.Data;

@Data
public class SensorOutputDto {

    private int id;

    private double latitude;

    private double longitude;

    private int temperature;

    private int humidity;

    private int pm25;

    private int pm10;

    private String location;

    public SensorOutputDto(Sensor sensor) {
        this.id = sensor.getId();
        this.latitude = sensor.getLatitude();
        this.longitude = sensor.getLongitude();
        this.temperature = sensor.getTemperature();
        this.humidity = sensor.getHumidity();
        this.pm10 = sensor.getPm10();
        this.pm25 = sensor.getPm25();
        this.location = sensor.getLocation();
    }

}
