package com.minidust.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddrToCoordsDto {
    private double longitude;
    private double latitude;
}
