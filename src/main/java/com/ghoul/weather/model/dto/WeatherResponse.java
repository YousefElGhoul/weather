package com.ghoul.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WeatherResponse(
        @JsonProperty("today") TodayWeather todayWeather,
        @JsonProperty("forecast") List<ForecastWeather> forecastWeather
) {
}
