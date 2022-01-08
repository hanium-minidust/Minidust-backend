package com.minidust.api.domain.pollution.models;


import com.minidust.api.global.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pollution_data")
public class PollutionData extends Timestamped {

    @Id
    @NotNull
    @Column(nullable = false)
    private String stationName;

    @NotNull
    @Column(nullable = false)
    private String sidoName;


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
