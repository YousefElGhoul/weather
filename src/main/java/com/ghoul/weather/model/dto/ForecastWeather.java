package com.ghoul.weather.model.dto;

public record ForecastWeather(
        String iconCode,
        String description,
        Temperature temperature
) {
}
