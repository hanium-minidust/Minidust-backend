package com.minidust.api.controller;

import com.minidust.api.models.Message;
import com.minidust.api.models.Sensor;
import com.minidust.api.models.SensorDto;
import com.minidust.api.models.StatusEnum;
import com.minidust.api.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class SensorController {
    private final SensorService sensorService;

    @GetMapping("/api/data")
    public List<Sensor> getAll() {
        return sensorService.getData();
    }

    @GetMapping("/api/data/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Sensor> data = sensorService.getById(id);
        Message message = new Message();
        if (data.isPresent()) {
            message = new Message(StatusEnum.OK, "OK", data.get());
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            message = new Message(StatusEnum.NOT_FOUND, "NOT FOUND");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/data")
    public ResponseEntity<?> updateOrCreate(@RequestBody @Valid SensorDto sensorDto, BindingResult errors) {
        Message message = new Message();
        if (errors.hasErrors()) { // 입력값이 유효한지 유효성을 확인합니다.
            message = new Message(
                    StatusEnum.BAD_REQUEST,
                    errors.getFieldError().getField() + " " + errors.getAllErrors().get(0).getDefaultMessage());
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        Sensor sensor = sensorService.updateOrCreate(sensorDto.getId(), sensorDto);
        message = new Message(StatusEnum.OK, "OK", sensor);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/api/data/{id}")
    public int deleteById(@PathVariable int id) {
        sensorService.deleteById(id);
        return id;
    }
}
