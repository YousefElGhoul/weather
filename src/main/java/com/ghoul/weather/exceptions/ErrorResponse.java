package com.ghoul.weather.exceptions;

public record ErrorResponse(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
