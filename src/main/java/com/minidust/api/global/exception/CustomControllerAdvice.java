package com.minidust.api.global.exception;

import com.minidust.api.global.response.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {
    // Controller에서 이뤄지는 Validation 이 실패할 경우 발생합니다.
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Message> handleConstraintViolation(ConstraintViolationException e) {
        return new ResponseEntity<>(Message.badRequest(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // IllegalArgumentException 을 핸들링합니다.
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Message> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(Message.badRequest(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // 찾는 데이터가 없는 경우에 통합으로 오류를 처리합니다.
    @ExceptionHandler(value = {DataNotFoundException.class})
    protected ResponseEntity<Message> dataNotFoundExceptionHandler(DataNotFoundException e) {
        return new ResponseEntity<>(Message.notFound(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {GpsNotValidException.class})
    protected ResponseEntity<Message> gpsNotValidExceptionHandler(GpsNotValidException e) {
        return new ResponseEntity<>(Message.badRequest(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
