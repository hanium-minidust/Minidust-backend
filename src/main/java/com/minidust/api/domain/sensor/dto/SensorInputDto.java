package com.minidust.api.domain.sensor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SensorInputDto {

    @NotNull(message = "ID의 값은 빈칸일 수 없습니다.")
    private int id;

    // 경도, 한국 기준 124~132
    @NotNull(message = "경도(longitude)의 값은 필수입니다.")
    @DecimalMin(value = "123", message = "경도(longitude)의 값은 123 보다 커야 합니다.")
    @DecimalMax(value = "133", message = "경도(longitude)의 값은 133보다 작아야 합니다.")
    private double longitude;

    // 위도, 33~43
    @DecimalMin(value = "32", message = "위도(latitude)의 값은 32보다 커야 합니다.")
    @DecimalMax(value = "44", message = "위도(latitude)의 값은 44보다는 작아야 합니다.")
    @NotNull(message = "위도(latitude)의 값은 필수입니다.")
    private double latitude;

    @NotNull(message = "온도(temperature)의 값은 필수입니다.")
    @Min(value = -50, message = "온도(temperature)는 -50도 보다 낮아질 수 없습니다.")
    @Max(value = 50, message = "온도(temperature)는 50도 보다 높아질 수 없습니다.")
    private int temperature;

    @NotNull(message = "습도(humidity)의 값은 필수입니다.")
    @Min(value = 1, message = "습도(humidity)의 최소값은 1입니다.")
    @Max(value = 100, message = "습도(humidity)의 최대 값은 100입니다.")
    private int humidity;

    @NotNull(message = "초미세먼지(pm25)의 값은 필수입니다.")
    @Min(value = 1, message = "초미세먼지(pm25)의 최소값은 1입니다.")
    @Max(value = 1000, message = "초미세먼지(pm25)의 값은 1000이 최대입니다.")
    private int pm25;

    @NotNull(message = "미세먼지(pm10)의 값은 필수입니다.")
    @Min(value = 1, message = "미세먼지(pm10)의 최소값은 1입니다.")
    @Max(value = 1000, message = "미세먼지(pm10)의 최대값은 1000입니다.")
    private int pm10;
}
