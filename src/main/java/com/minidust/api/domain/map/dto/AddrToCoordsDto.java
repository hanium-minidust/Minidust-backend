package com.minidust.api.domain.map.dto;

import lombok.Data;

@Data
public class AddrToCoordsDto {
    private double longitude;
    private double latitude;

    public AddrToCoordsDto(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
