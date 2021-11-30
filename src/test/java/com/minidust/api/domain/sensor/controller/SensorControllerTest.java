package com.minidust.api.domain.sensor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minidust.api.domain.sensor.dto.SensorDto;
import com.minidust.api.domain.sensor.models.Sensor;
import com.minidust.api.domain.sensor.service.SensorService;
import com.minidust.api.global.response.Message;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(SensorController.class)
class SensorControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SensorService sensorService;

    /*
    CREATE(POST)
     */
    @Test
    void 생성시도_정상작동() throws Exception {
        when(sensorService.upload(any(SensorDto.class))).thenReturn(new Sensor(getCorrectSensorDto(), "No location"));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/sensor/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getCorrectSensorDto())))
                .andExpect(status().isOk())
                .andReturn();

        Message message = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Message.class);
        Sensor sensor1 = objectMapper.convertValue(message.getData(), Sensor.class);
        Sensor sensor2 = new Sensor(getCorrectSensorDto(), "No location");

        assertThat(isEqualSensor(sensor1, sensor2)).isTrue();
    }

    @Test
    void 생성시도_하지만_값오류() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/sensor/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getWrongSensorDto())))
                .andExpect(status().isBadRequest());
    }

    /*
    READ(GET)
     */
    @Test
    void 생성된거_받아오기() throws Exception {
        // given
        Sensor sensor = new Sensor(getCorrectSensorDto(), "위치 정보 없음");
        when(sensorService.findById(2000)).thenReturn(sensor);

        //
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/sensor/2000"))
                .andExpect(status().isOk())
                .andReturn();

        Message message = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Message.class);
        Sensor sensor1 = objectMapper.convertValue(message.getData(), Sensor.class);

        assertThat(isEqualSensor(sensor, sensor1)).isTrue();
    }

    @Test
    void 없는거_받아오기() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/sensor/100000"))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andReturn();

        Message message = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Message.class);

        assertThat(message.getData()).isNull();
    }

    public SensorDto getCorrectSensorDto() {
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

    // 같은 센서 객체인지 검증하기
    public boolean isEqualSensor(Sensor sensor1, Sensor sensor2) {
        return (sensor1.getId() == sensor2.getId()) &&
                (sensor1.getHumidity() == sensor2.getHumidity()) &&
                (sensor1.getTemperature() == sensor2.getTemperature()) &&
                (sensor1.getLongitude() == sensor2.getLongitude()) &&
                (sensor1.getLatitude() == sensor2.getLatitude()) &&
                (sensor1.getPm10() == sensor2.getPm10()) &&
                (sensor1.getPm25() == sensor2.getPm25());
    }
}