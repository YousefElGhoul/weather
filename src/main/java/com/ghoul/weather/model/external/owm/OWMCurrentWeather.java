package com.ghoul.weather.model.external.owm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OWMCurrentWeather {
    private double temp;
    @JsonProperty("feels_like") private double feelsLike;
    private OWMWeatherDescription weather;
}
