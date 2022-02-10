package com.minidust.api.domain.pollution.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoordsDto {

    double longitude;

    double latitude;
}
