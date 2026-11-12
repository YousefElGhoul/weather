package com.ghoul.weather.exceptions;

public class InvalidExternalResponseException extends RuntimeException {
    public InvalidExternalResponseException(String message) {
        super(message);
    }
}
