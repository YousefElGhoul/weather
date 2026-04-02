package com.ghoul.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TodayWeather(
        @JsonProperty("icon_code") String iconCode,
        String description,
        @JsonProperty("temp") Temperature temperature,
        @JsonProperty("midnight") Double midnightTemp,
        @JsonProperty("morning") Double morningTemp,
        @JsonProperty("noon") Double noonTemp,
        @JsonProperty("evening") Double eveningTemp
) {
}
