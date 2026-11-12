package com.ghoul.weather.model.external.owm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OWMDailyItem {
    private OWMDailyTemp temp;
    @JsonProperty("feels_like") private OWMDailyFeelsLike feelsLike;
    private List<OWMWeatherDescription> weather;
}
