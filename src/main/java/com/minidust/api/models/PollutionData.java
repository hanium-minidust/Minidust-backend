package com.minidust.api.models;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

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

    @NotNull
    @Column(nullable = false)
    private String sidoName;

    @NotNull
    @Column(nullable = false)
    private String stationName;

    @NotNull
    @DecimalMin("123")
    @DecimalMax("133")
    @Column(nullable = false)
    private Double longitude;

    @NotNull
    @DecimalMin("32")
    @DecimalMax("44")
    @Column(nullable = false)
    private Double latitude;

    @NotNull
    @Min(1)
    @Max(1000)
    @Column(nullable = false)
    private int pm25;

    @NotNull
    @Min(1)
    @Max(1000)
    @Column(nullable = false)
    private int pm10;
}
