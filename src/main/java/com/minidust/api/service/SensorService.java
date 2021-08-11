package com.minidust.api.service;

import com.minidust.api.models.Sensor;
import com.minidust.api.models.SensorDto;
import com.minidust.api.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    public List<Sensor> getData() {
        return sensorRepository.findAll();
    }

    public Optional<Sensor> getById(int id) {
        Optional<Sensor> data = sensorRepository.findById(id);
        return data;
    }

//    @Transactional
//    public Sensor updateData(int id, SensorDto sensorDto) {
//        Sensor sensor = sensorRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        sensor.update(sensorDto);
//        return sensor;
//    }
//
//    public Sensor createData(SensorDto sensorDto) {
//        Sensor sensor = new Sensor(sensorDto);
//        return sensorRepository.save(sensor);
//    }

    @Transactional
    public Sensor updateOrCreate(int id, SensorDto sensorDto) {
        Optional<Sensor> sensorOptional = sensorRepository.findById(id);
        if (sensorOptional.isPresent()) {
            sensorOptional.get().update(sensorDto);
            return sensorOptional.get();
        } else {
            Sensor newSensor = new Sensor(sensorDto);
            return sensorRepository.save(newSensor);
        }
    }

    public void deleteById(int id) {
        sensorRepository.deleteById(id);
    }
}
