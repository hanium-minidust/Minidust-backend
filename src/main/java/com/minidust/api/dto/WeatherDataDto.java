package com.minidust.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
public class WeatherDataDto {

    public WeatherDataDto(String name, String icon, int temperature, int humidity) {
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
