package com.minidust.api.domain.sensor.controller;

import com.minidust.api.domain.sensor.service.SensorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SensorController.class)
class SensorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SensorService sensorService;

    @Test
    void GET_ALL_정상작동() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/sensor"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}