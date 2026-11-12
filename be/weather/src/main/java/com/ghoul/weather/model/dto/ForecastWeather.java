package com.ghoul.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ForecastWeather(
        @JsonProperty("icon_code") String iconCode,
        String description,
        Temperature temperature
) {
}
