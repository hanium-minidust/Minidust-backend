package com.minidust.api.controller;

import com.minidust.api.dto.SensorDto;
import com.minidust.api.exception.DataNotFoundException;
import com.minidust.api.models.Message;
import com.minidust.api.models.Sensor;
import com.minidust.api.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

// TODO Exception 처리는 Service 단으로 넘어가자. 그래야 테스트케이스 작성이 가능할 것 같다.

@RequiredArgsConstructor
@RestController
public class SensorController {
    private final SensorService sensorService;

    @GetMapping("/api/data")
    public ResponseEntity<?> findAll() {
        List<Sensor> result = sensorService.findAll();
        return new ResponseEntity<>(Message.getDefaultOkMessage(result), HttpStatus.OK);
    }

    @GetMapping("/api/data/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        Sensor sensor = sensorService.findById(id).orElseThrow(() -> new DataNotFoundException("해당 ID값을 가진 데이터를 찾을 수 없습니다."));
        return new ResponseEntity<>(Message.getDefaultOkMessage(sensor), HttpStatus.OK);
    }

    @PostMapping("/api/data")
    public ResponseEntity<?> uploadData(@RequestBody @Valid SensorDto sensorDto, BindingResult errors) {
        if (errors.hasErrors()) {
            String message = errors.getAllErrors().get(0).getDefaultMessage();
            throw new IllegalArgumentException(message);
        }
        Sensor sensor = sensorService.updateOrCreate(sensorDto);
        return new ResponseEntity<>(Message.getDefaultOkMessage(sensor), HttpStatus.OK);
    }

    @DeleteMapping("/api/data/{id}")
    public int deleteById(@PathVariable int id) {
        sensorService.deleteById(id);
        return id;
    }
}
