package com.ghoul.weather.exceptions;

public record ErrorResponse(
        String error,
        String message
) {
}
