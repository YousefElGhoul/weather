package com.ghoul.weather.exceptions;

public class UpstreamTimeoutException extends RuntimeException {
    public UpstreamTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
