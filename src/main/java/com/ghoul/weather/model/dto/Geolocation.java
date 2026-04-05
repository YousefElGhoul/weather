package com.ghoul.weather.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Geolocation(
        Double lat,
        Double lon,
        String city,
        @JsonProperty("country_code") String countryCode
) {
}
