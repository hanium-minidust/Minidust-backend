package com.minidust.api.domain.sensor.service;

import com.minidust.api.domain.sensor.dto.SensorDto;
import com.minidust.api.domain.sensor.models.Sensor;
import com.minidust.api.domain.sensor.repository.SensorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @Mock
    SensorRepository sensorRepository;

    @InjectMocks
    SensorService sensorService;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void 센서_생성_성공() {
        // given
        SensorDto sensorDto = SensorDto.builder()
                .id(2000)
                .humidity(50)
                .temperature(23)
                .latitude(37.125)
                .longitude(127.37)
                .pm10(15)
                .pm25(15)
                .build();

        Sensor sensor1 = new Sensor(sensorDto, "경기도 용인시 처인구");

        // when
        when(sensorRepository.save(any())).thenReturn(new Sensor(sensorDto));
        Sensor sensor = sensorService.createData(sensorDto);

        // Then
        assertThat(sensor.getLongitude()).isEqualTo(sensor1.getLongitude());
        assertThat(sensor.getLatitude()).isEqualTo(sensor1.getLatitude());
        assertThat(sensor.getTemperature()).isEqualTo(sensor1.getTemperature());
        assertThat(sensor.getHumidity()).isEqualTo(sensor1.getHumidity());
        assertThat(sensor.getPm25()).isEqualTo(sensor1.getPm25());
        assertThat(sensor.getPm10()).isEqualTo(sensor1.getPm10());
    }

    @Test
    void 센서_생성_안되게() {
        // given
        SensorDto sensorDto = SensorDto.builder()
                .id(2000)
                .humidity(1550)
                .temperature(23)
                .latitude(37.125)
                .longitude(127.37)
                .pm10(15)
                .pm25(15)
                .build();

        // when
        // 위반한 사항들에 대해서 Set에 담기게 됩니다.
        Set<ConstraintViolation<SensorDto>> violations = validator.validate(sensorDto);

        // then
        assertFalse(violations.isEmpty());
    }
}