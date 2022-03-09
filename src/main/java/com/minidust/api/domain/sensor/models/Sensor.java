package com.minidust.api.domain.sensor.models;

import com.minidust.api.domain.sensor.dto.SensorInputDto;
import com.minidust.api.global.util.Timestamped;
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

    @Column(nullable = false)
    private String location;

    public Sensor(SensorInputDto sensorInputDto, String location) {
        this.id = sensorInputDto.getId();
        this.longitude = sensorInputDto.getLongitude();
        this.latitude = sensorInputDto.getLatitude();
        this.temperature = sensorInputDto.getTemperature();
        this.humidity = sensorInputDto.getHumidity();
        this.pm25 = sensorInputDto.getPm25();
        this.pm10 = sensorInputDto.getPm10();
        this.location = location;
    }

    public void update(SensorInputDto sensorInputDto, String location) {
        this.longitude = sensorInputDto.getLongitude();
        this.latitude = sensorInputDto.getLatitude();
        this.temperature = sensorInputDto.getTemperature();
        this.humidity = sensorInputDto.getHumidity();
        this.pm25 = sensorInputDto.getPm25();
        this.pm10 = sensorInputDto.getPm10();
        this.location = location;
    }
}
