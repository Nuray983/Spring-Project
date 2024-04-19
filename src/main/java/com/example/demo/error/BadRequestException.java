package com.example.demo.error;

public class BadRequestException extends Throwable {
    public BadRequestException(String message) {
        super(message);
    }
}
