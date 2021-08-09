package com.minidust.api.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "pollution_data")
public class PollutionData {

    public PollutionData(String sidoName, String stationName, Double longitude, Double latitude, int pm25, int pm10) {
        this.sidoName = sidoName;
        this.stationName = stationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pm25 = pm25;
        this.pm10 = pm10;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String sidoName;

    @Column(nullable = false)
    private String stationName;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private int pm25;

    @Column(nullable = false)
    private int pm10;
}
