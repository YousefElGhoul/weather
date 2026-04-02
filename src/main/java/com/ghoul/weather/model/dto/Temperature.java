package com.ghoul.weather.model.dto;

public record Temperature(
        Double temp,
        Double feelsLike,
        Double low,
        Double high
) {
}
