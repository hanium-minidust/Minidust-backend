package com.minidust.api.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name="sensor_data")
@NoArgsConstructor
public class Sensor extends Timestamped {

    @Id
    private int id;

    // TODO 위도와 경도값 입력했을때 한국 범위안에 존재하는지 확인하는지.
    @Column(nullable = false)
    private double latitude;

    // 경도, 한국 기준 124~132
    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double temperature;

    @Column(nullable = false)
    private double humidity;

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
