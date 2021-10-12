package com.minidust.api.global.response;

import lombok.Data;

@Data
public class Message {
    private StatusEnum status;
    private String message;
    private Object data;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }

    public Message(StatusEnum status, String message) {
        this.status = status;
        this.message = message;
        this.data = null;
    }

    public Message(StatusEnum status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static Message badRequest() {
        return new Message(StatusEnum.BAD_REQUEST, null, null);
    }

    public static Message badRequest(String message) {
        return new Message(StatusEnum.BAD_REQUEST, message, null);
    }

    public static Message notFound() {
        return new Message(StatusEnum.NOT_FOUND, null, null);
    }

    public static Message notFound(String message) {
        return new Message(StatusEnum.NOT_FOUND, message, null);
    }

    public static Message ok() {
        return new Message(StatusEnum.OK, null, null);
    }

    public static Message ok(Object data) {
        return new Message(StatusEnum.OK, "OK", data);
    }
}