package com.minidust.api.domain.sensor.Integration;

import com.minidust.api.domain.sensor.dto.SensorDto;
import com.minidust.api.domain.sensor.models.Sensor;
import com.minidust.api.domain.sensor.repository.SensorRepository;
import com.minidust.api.domain.sensor.service.SensorService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class SensorIntegrationTest {

    @Autowired
    SensorRepository sensorRepository;

    @Autowired
    SensorService sensorService;

    SensorDto sensorDto;

    @Before
    public void init() {
        sensorDto = SensorDto.builder()
                .id(2000)
                .humidity(50)
                .temperature(23)
                .latitude(37.125)
                .longitude(127.37)
                .pm10(15)
                .pm25(15)
                .build();
    }

    @Test
    public void 센서_업로드_정상() {
        // given

        // when
        Sensor sensor = sensorService.upload(sensorDto);

        // then
        Sensor sensor1 = sensorRepository.findById(sensorDto.getId()).get();
        System.out.println("sensor = " + sensor);
        System.out.println("sensor1 = " + sensor1);
        assertThat(sensor).isEqualTo(sensor1);
    }

    @Test
    public void 센서_업데이트_정상() {
        // given
        Sensor sensor = sensorService.upload(sensorDto); // 센서를 업로드 하고
        Sensor sensor1 = sensorRepository.findById(sensorDto.getId()).get(); // 업로드 된 센서가 주어졌을때

        // when
        SensorDto sensorDto2 = SensorDto.builder()
                .id(2000)
                .humidity(70) // 습도의 값 변경됨
                .temperature(36) // 온도 값 변경됨
                .latitude(37.125)
                .longitude(127.37)
                .pm10(15)
                .pm25(15)
                .build();

        sensor1.update(sensorDto2, sensor1.getLocation());
        // then
        assertThat(sensor1.getHumidity()).isEqualTo(70);
        assertThat(sensor1.getTemperature()).isEqualTo(36);
    }
}
