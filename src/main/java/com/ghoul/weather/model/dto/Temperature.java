package com.ghoul.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Temperature(
        Double temp,
        @JsonProperty("feels_like") Double feelsLike,
        Double low,
        Double high
) {
}
