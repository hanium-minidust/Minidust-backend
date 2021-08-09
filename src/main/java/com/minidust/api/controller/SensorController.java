package com.minidust.api.controller;

import com.minidust.api.models.Sensor;
import com.minidust.api.models.SensorDto;
import com.minidust.api.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class SensorController {
    private final SensorService sensorService;

    @GetMapping("/api/data")
    public Map<String, Object> getAllData() {
        Map<String, Object> response = new HashMap<>();
        for (Sensor sensor : sensorService.getAllData()) {
            response.put("result", sensor);
        }
        return response;
    }

    @GetMapping("/api/data/{id}")
    public Map<String, Object> getDataById(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Sensor> data = sensorService.getDataById(id);
        if (data.isPresent()) {
            response.put("result", data.get());
        } else {
            response.put("result", "FAIL");
        }
        return response;
    }

    @PostMapping("/api/data")
    public int createData(@RequestBody SensorDto sensorDto) {
        int id = sensorDto.getId();
        Optional<Sensor> data = sensorService.getDataById(id);
        if (data.isPresent()) {
            sensorService.updateData(id, sensorDto);
        } else {
            sensorService.createData(sensorDto);
        }
        return id;
    }

    @DeleteMapping("/api/data/{id}")
    public int deleteData(@PathVariable int id) {
        sensorService.deleteDataById(id);
        return id;
    }
}
