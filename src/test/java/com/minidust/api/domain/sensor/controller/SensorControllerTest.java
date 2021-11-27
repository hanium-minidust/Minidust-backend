package com.minidust.api.domain.sensor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minidust.api.domain.sensor.dto.SensorDto;
import com.minidust.api.domain.sensor.service.SensorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorController.class)
class SensorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SensorService sensorService;


    @Test
    void 생성시도_정상작동() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/sensor/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getRightSensorDto())))
                .andExpect(status().isOk());
    }

    @Test
    void 생성시도_하지만_값오류() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/sensor/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getWrongSensorDto())))
                .andExpect(status().isBadRequest());
    }

    public SensorDto getRightSensorDto() {
        return SensorDto.builder()
                .id(2000)
                .humidity(50)
                .temperature(23)
                .latitude(37.125)
                .longitude(127.37)
                .pm10(15)
                .pm25(15)
                .build();
    }

    public SensorDto getWrongSensorDto() {
        return SensorDto.builder()
                .id(2000)
                .humidity(1000) // 값 범위 초과
                .temperature(1000) // 값 범위 초과
                .latitude(37.125)
                .longitude(127.37)
                .pm10(15)
                .pm25(15)
                .build();
    }
}