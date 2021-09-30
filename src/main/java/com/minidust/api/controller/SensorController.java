package com.minidust.api.controller;

import com.minidust.api.dto.SensorDto;
import com.minidust.api.models.Message;
import com.minidust.api.models.Sensor;
import com.minidust.api.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// TODO Exception 처리는 Service 단으로 넘어가자. 그래야 테스트케이스 작성이 가능할 것 같다.
// https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc <- 공부

@RequiredArgsConstructor
@RestController
public class SensorController {
    private final SensorService sensorService;

    @GetMapping("/api/data")
    public ResponseEntity<Message> findAll() {
        List<Sensor> result = sensorService.findAll();
        return ResponseEntity.ok(Message.getDefaultOkMessage(result));
    }

    @GetMapping("/api/data/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        Sensor sensor = sensorService.findById(id);
        return ResponseEntity.ok(Message.getDefaultOkMessage(sensor));
    }

    @PostMapping("/api/data")
    public ResponseEntity<Message> uploadData(@RequestBody @Valid SensorDto sensorDto, BindingResult errors) {
        if (errors.hasErrors()) {
            throw new IllegalArgumentException(errors.getAllErrors().get(0).getDefaultMessage());
        }
        Sensor sensor = sensorService.updateOrCreate(sensorDto);
        return ResponseEntity.ok(Message.getDefaultOkMessage(sensor));
    }

    @DeleteMapping("/api/data/{id}")
    public ResponseEntity<Message> deleteById(@PathVariable int id) {
        sensorService.deleteById(id);
        return ResponseEntity.ok(Message.getDefaultOkMessage(id));
    }
}
