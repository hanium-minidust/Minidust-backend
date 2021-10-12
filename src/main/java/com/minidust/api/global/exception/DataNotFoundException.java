package com.minidust.api.global.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException() {
        super();
    }
}
