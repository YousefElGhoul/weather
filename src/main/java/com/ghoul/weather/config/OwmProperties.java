package com.ghoul.weather.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "owm")
public record OwmProperties(
        @NotBlank(message = "OWM_API_KEY must be configured") String apiKey,
        @NotBlank String baseUrl
) {
}
