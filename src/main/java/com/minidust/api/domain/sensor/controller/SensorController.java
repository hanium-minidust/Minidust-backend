package com.minidust.api.domain.sensor.controller;

import com.minidust.api.domain.sensor.dto.SensorDto;
import com.minidust.api.domain.sensor.models.Sensor;
import com.minidust.api.domain.sensor.service.SensorService;
import com.minidust.api.global.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc <- 공부

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SensorController {
    private final SensorService sensorService;

    @GetMapping("/sensor")
    public ResponseEntity<Message> findAll() {
        List<Sensor> result = sensorService.findAll();
        return ResponseEntity.ok(Message.ok(result));
    }

    @GetMapping("/sensor/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        Sensor sensor = sensorService.findById(id);
        return ResponseEntity.ok(Message.ok(sensor));
    }

    @PostMapping("/sensor/new")
    public ResponseEntity<Message> uploadData(@RequestBody @Valid SensorDto sensorDto, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
        }
        Sensor sensor = sensorService.updateOrCreate(sensorDto);
        return ResponseEntity.ok(Message.ok(sensor));
    }

    @DeleteMapping("/sensor/{id}")
    public ResponseEntity<Message> deleteById(@PathVariable int id) {
        sensorService.deleteById(id);
        return ResponseEntity.ok(Message.ok(id));
    }
}
