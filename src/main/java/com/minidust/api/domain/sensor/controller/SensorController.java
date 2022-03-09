package com.minidust.api.domain.sensor.controller;

import com.minidust.api.domain.sensor.dto.SensorInputDto;
import com.minidust.api.domain.sensor.dto.SensorOutputDto;
import com.minidust.api.domain.sensor.service.SensorService;
import com.minidust.api.global.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc <- 공부

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SensorController {
    private final SensorService sensorService;

    @GetMapping("/sensor")
    public ResponseEntity<Message> findAll() {
        List<SensorOutputDto> result = sensorService.findAll().stream().map(SensorOutputDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(Message.ok(result));
    }

    @GetMapping("/sensor/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        SensorOutputDto sensor = new SensorOutputDto(sensorService.findById(id));
        return ResponseEntity.ok(Message.ok(sensor));
    }

    @PostMapping("/sensor/new")
    public ResponseEntity<Message> uploadData(@RequestBody @Valid SensorInputDto sensorInputDto, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
        }

        SensorOutputDto sensor = new SensorOutputDto(sensorService.upload(sensorInputDto));
        return ResponseEntity.ok(Message.ok(sensor));
    }

    @DeleteMapping("/sensor/{id}")
    public ResponseEntity<Message> deleteById(@PathVariable int id) {
        sensorService.deleteById(id);
        return ResponseEntity.ok(Message.ok(id));
    }
}
