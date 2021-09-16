package com.minidust.api.service;

import com.minidust.api.dto.SensorDto;
import com.minidust.api.models.Sensor;
import com.minidust.api.repository.SensorRepository;
import com.minidust.api.util.BiCoordsToAddr;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    /**
     * 미세먼지 측정기 데이터 읽기(READ)
     */
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> findById(int id) {
        Optional<Sensor> data = sensorRepository.findById(id);
        return data;
    }

    /**
     * 미세먼지 측정기 데이터 생성/수정(CREATE, UPDATE)
     */
    @Transactional
    public Sensor updateOrCreate(SensorDto sensorDto) {
        if (isExistId(sensorDto.getId())) {
            return updateData(sensorDto);
        } else {
            return createData(sensorDto);
        }
    }

    // 해당 ID값을 가진 데이터가 없으므로 생성합니다.
    public Sensor createData(SensorDto sensorDto) {
        String location = getLocation(sensorDto.getLongitude(), sensorDto.getLatitude());
        Sensor sensor = new Sensor(sensorDto, location);
        return sensorRepository.save(sensor);
    }

    // 해당 ID값을 가진 데이터가 있으므로 수정합니다.
    @Transactional
    public Sensor updateData(SensorDto sensorDto) {
        // Sensor 정보를 가져옵니다. 이미 ID에 대한 검사를 진행했으므로 NULL 가능성이 없습니다.
        Sensor sensor = sensorRepository.findById(sensorDto.getId()).get();
        String location = getLocation(sensorDto.getLongitude(), sensorDto.getLatitude());
        sensor.update(sensorDto, location);
        return sensor;
    }

    // 해당 ID값을 가진 데이터가 있는지 확인합니다.
    public boolean isExistId(int id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return sensor.isPresent();
    }

    /**
     * 미세먼지 측정기 데이터 삭제(DELETE)
     */

    public void deleteById(int id) {
        sensorRepository.deleteById(id);
    }

    /**
     * 측정기의 위치정보를 위한 함수입니다.
     */
    public String getLocation(double longitude, double latitude) {
        List<String> result = BiCoordsToAddr.getAddressFromCoordinates(longitude, latitude);
        return result.get(0) + " " + result.get(2);
    }
}
