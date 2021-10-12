package com.minidust.api.domain.map.dto;

import lombok.Data;

@Data
public class CoordsToAddrDto {
    private String first;
    private String alias;
    private String second;
    private String third;

    public CoordsToAddrDto(String first, String alias, String second, String third) {
        this.first = first;
        this.alias = alias;
        this.second = second;
        this.third = third;
    }
}
