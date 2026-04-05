package com.ghoul.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WeatherResponse(
        @JsonProperty("city") String city,
        @JsonProperty("country_code") String countryCode,
        @JsonProperty("today") TodayWeather todayWeather,
        @JsonProperty("forecast") List<ForecastWeather> forecastWeather
) {
}
