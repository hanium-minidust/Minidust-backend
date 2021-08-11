package com.minidust.api.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Entity
@Table(name="sensor_data")
@NoArgsConstructor
public class Sensor extends Timestamped {

    @NotNull
    @Id
    private int id;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private int temperature;

    @Column(nullable = false)
    private int humidity;

    @Column(nullable = false)
    private int pm25;

    @Column(nullable = false)
    private int pm10;

    public Sensor(SensorDto sensorDto) {
        this.id = sensorDto.getId();
        this.longitude = sensorDto.getLongitude();
        this.latitude = sensorDto.getLatitude();
        this.temperature = sensorDto.getTemperature();
        this.humidity = sensorDto.getHumidity();
        this.pm25 = sensorDto.getPm25();
        this.pm10 = sensorDto.getPm10();
    }

    public void update(SensorDto sensorDto) {
        this.id = sensorDto.getId();
        this.longitude = sensorDto.getLongitude();
        this.latitude = sensorDto.getLatitude();
        this.temperature = sensorDto.getTemperature();
        this.humidity = sensorDto.getHumidity();
        this.pm25 = sensorDto.getPm25();
        this.pm10 = sensorDto.getPm10();
    }
}
