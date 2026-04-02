package com.ghoul.weather.model.external.owm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OWMWeatherDescription {
    @JsonProperty("id") private Integer id;
    @JsonProperty("main") private String main;
    @JsonProperty("description") private String description;
    @JsonProperty("icon") private String icon;
}
