package com.minidust.api.domain.sensor.models;

import com.minidust.api.domain.sensor.dto.SensorDto;
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

    public Sensor(SensorDto sensorDto, String location) {
        this.id = sensorDto.getId();
        this.longitude = sensorDto.getLongitude();
        this.latitude = sensorDto.getLatitude();
        this.temperature = sensorDto.getTemperature();
        this.humidity = sensorDto.getHumidity();
        this.pm25 = sensorDto.getPm25();
        this.pm10 = sensorDto.getPm10();
        this.location = location;
    }

    public void update(SensorDto sensorDto, String location) {
        this.id = sensorDto.getId();
        this.longitude = sensorDto.getLongitude();
        this.latitude = sensorDto.getLatitude();
        this.temperature = sensorDto.getTemperature();
        this.humidity = sensorDto.getHumidity();
        this.pm25 = sensorDto.getPm25();
        this.pm10 = sensorDto.getPm10();
        this.location = location;
    }
}
