package com.ghoul.weather.client;

import com.ghoul.weather.model.dto.Geolocation;
import com.ghoul.weather.model.external.owm.OWMResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OwmClient {
    @Value("${owm.api-key}")
    private String apiKey;

    private final RestClient restClient;

    public OwmClient(@Qualifier("owmRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public OWMResponse getOwmResponse(Geolocation geolocation) {
        double lat = geolocation.lat();
        double lon = geolocation.lon();

        return restClient.get()
                .uri("?lat={lat}&lon={lon}&exclude=minutely,hourly,alerts&appid={apiKey}&units=metric", lat, lon, apiKey)
                .retrieve()
                .body(OWMResponse.class);
    }
}
