package com.minidust.api.models;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "pollution_data")
public class PollutionData extends Timestamped {

    public PollutionData(Long id, String sidoName, String stationName, Double longitude, Double latitude, int pm25, int pm10) {
        this.id = id;
        this.sidoName = sidoName;
        this.stationName = stationName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.pm25 = pm25;
        this.pm10 = pm10;
    }

    @Id
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
    @Min(0)
    @Max(1000)
    @Column(nullable = false)
    private int pm25;

    @NotNull
    @Min(0)
    @Max(1000)
    @Column(nullable = false)
    private int pm10;

    public void update(PollutionData pollutionData) {
        this.pm10 = pollutionData.getPm10();
        this.pm25 = pollutionData.getPm25();
    }
}
