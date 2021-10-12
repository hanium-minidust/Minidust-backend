package com.minidust.api.global.exception;

public class GpsNotValidException extends RuntimeException {
    public GpsNotValidException() {
        super("GPS 위치 값이 올바르지 않습니다.");
    }

    public GpsNotValidException(String message) {
        super(message);
    }
}
