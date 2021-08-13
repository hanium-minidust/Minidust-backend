package com.minidust.api.controller;

import com.minidust.api.models.Message;
import com.minidust.api.models.Sensor;
import com.minidust.api.models.SensorDto;
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
    public ResponseEntity<?> getAll() {
        List<Sensor> result = sensorService.getData();
        return new ResponseEntity<>(Message.getDefaultOkMessage(result), HttpStatus.OK);
    }

    @GetMapping("/api/data/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Sensor> data = sensorService.getById(id);
        if (data.isPresent()) {
            return new ResponseEntity<>(Message.getDefaultOkMessage(data.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Message.getDefaultNotFoundMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/data")
    public ResponseEntity<?> updateOrCreate(@RequestBody @Valid SensorDto sensorDto, BindingResult errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getField() + " " + errors.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(Message.getDefaultBadRequestMessage(message), HttpStatus.BAD_REQUEST);
        }
        Sensor sensor = sensorService.updateOrCreate(sensorDto.getId(), sensorDto);
        return new ResponseEntity<>(Message.getDefaultOkMessage(sensor), HttpStatus.OK);
    }

    @DeleteMapping("/api/data/{id}")
    public int deleteById(@PathVariable int id) {
        sensorService.deleteById(id);
        return id;
    }
}
