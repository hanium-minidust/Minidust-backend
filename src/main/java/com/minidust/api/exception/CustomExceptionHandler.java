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
    protected ResponseEntity<?> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
        return new ResponseEntity<>(Message.getDefaultBadRequestMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    protected ResponseEntity<?> DataNotFoundExceptionHandler(DataNotFoundException e) {
        return new ResponseEntity<>(Message.getDefaultNotFoundMessage(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
