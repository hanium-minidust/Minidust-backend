package com.minidust.api.exception;

import com.minidust.api.models.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<?> handleConstraintViolation(ConstraintViolationException e) {
        return new ResponseEntity<>(Message.getDefaultBadRequestMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // IllegalArgumentException 을 핸들링합니다.
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(Message.getDefaultBadRequestMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // 찾는 데이터가 없는 경우에 통합으로 오류를 처리합니다.
    @ExceptionHandler(value = {DataNotFoundException.class})
    protected ResponseEntity<?> dataNotFoundExceptionHandler(DataNotFoundException e) {
        return new ResponseEntity<>(Message.getDefaultNotFoundMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {GpsNotValidException.class})
    protected ResponseEntity<?> gpsNotValidExceptionHandler(GpsNotValidException e) {
        return new ResponseEntity<>(Message.getDefaultBadRequestMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
