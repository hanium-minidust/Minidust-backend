package com.minidust.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CoordsToAddrDto {
    private String first;
    private String alias;
    private String second;
    private String third;
}
