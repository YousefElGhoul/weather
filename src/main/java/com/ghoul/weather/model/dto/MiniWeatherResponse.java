package com.ghoul.weather.model.dto;

public record MiniWeatherResponse(
        String description,
        Double temperature
) {
}
