package com.minidust.api.domain.map.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddrToCoordsDto {
    private double longitude;
    private double latitude;

    public AddrToCoordsDto(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
