package com.minidust.api.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "weather_data")
public class WeatherData {

    public WeatherData(String name, String icon, int temperature, int humidity) {
        this.name = name;
        this.icon = icon;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String icon;

    private int temperature;

    private int humidity;

    private String name;
}
