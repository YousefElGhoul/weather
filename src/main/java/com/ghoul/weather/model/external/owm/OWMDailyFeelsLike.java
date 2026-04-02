package com.ghoul.weather.model.external.owm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OWMDailyFeelsLike {
    @JsonProperty("night") private Double midnight;
    @JsonProperty("morn") private Double morning;
    @JsonProperty("day") private Double noon;
    @JsonProperty("eve") private Double evening;
}
