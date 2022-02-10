package com.minidust.api.domain.sensor.service;

import com.minidust.api.domain.map.dto.CoordsToAddrDto;
import com.minidust.api.domain.map.service.MapService;
import com.minidust.api.domain.sensor.dto.SensorDto;
import com.minidust.api.domain.sensor.models.Sensor;
import com.minidust.api.domain.sensor.repository.SensorRepository;
import com.minidust.api.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    /**
     * 미세먼지 측정기 데이터 읽기(READ)
     */
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Sensor findById(int id) {
        return sensorRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당 ID값을 가진 데이터를 찾을 수 없습니다."));
    }

    /**
     * 미세먼지 측정기 데이터 생성/수정(CREATE, UPDATE)
     */
    @Transactional
    public Sensor upload(SensorDto sensorDto) {
        Optional<Sensor> sensorOptional = sensorRepository.findById(sensorDto.getId());
        if (sensorOptional.isPresent()) {
            Sensor sensor = sensorOptional.get();
            sensor.update(sensorDto, getLocation(sensorDto.getLongitude(), sensorDto.getLatitude()));
            return sensor;
        } else {
            Sensor sensor = new Sensor(sensorDto, getLocation(sensorDto.getLongitude(), sensorDto.getLatitude()));
            return sensorRepository.save(sensor);
        }
    }

    /**
     * 미세먼지 측정기 데이터 삭제(DELETE)
     */
    @Transactional
    public void deleteById(int id) {
        sensorRepository.deleteById(id);
    }

    /**
     * 측정기의 위치정보를 위한 함수입니다.
     */
    public String getLocation(double longitude, double latitude) {
        CoordsToAddrDto addressFromCoordinates = MapService.getAddressFromCoordinates(longitude, latitude);
        if (!MapService.isCorrectCoords(longitude, latitude)) {
            return "위치사용 불가";
        }
        return addressFromCoordinates.getFirst() + addressFromCoordinates.getSecond();
    }
}
