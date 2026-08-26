package com.ghoul.weather.client;

import com.ghoul.weather.model.dto.Geolocation;
import com.ghoul.weather.config.OwmProperties;
import com.ghoul.weather.model.external.owm.OWMResponse;
import com.ghoul.weather.exceptions.UpstreamServiceException;
import com.ghoul.weather.exceptions.UpstreamTimeoutException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.net.SocketTimeoutException;

@Component
public class OwmClient {
    private final String apiKey;
    private final RestClient restClient;

    public OwmClient(@Qualifier("owmRestClient") RestClient restClient, OwmProperties properties) {
        this.restClient = restClient;
        this.apiKey = properties.apiKey();
    }

    @Cacheable(cacheNames = "weatherForecast", keyGenerator = "weatherCacheKeyGenerator", unless = "#result == null")
    public OWMResponse getOwmResponse(Geolocation geolocation) {
        double lat = geolocation.lat();
        double lon = geolocation.lon();

        try {
            OWMResponse response = restClient.get()
                    .uri("?lat={lat}&lon={lon}&exclude=minutely,hourly,alerts&appid={apiKey}&units=metric", lat, lon, apiKey)
                    .retrieve()
                    .body(OWMResponse.class);
            if (response == null) {
                throw new UpstreamServiceException("Weather provider returned an empty response", null);
            }
            return response;
        } catch (ResourceAccessException ex) {
            if (ex.getCause() instanceof SocketTimeoutException) {
                throw new UpstreamTimeoutException("Weather provider timed out", ex);
            }
            throw new UpstreamServiceException("Weather provider is unreachable", ex);
        } catch (RestClientResponseException ex) {
            throw new UpstreamServiceException("Weather provider returned HTTP " + ex.getStatusCode(), ex);
        }
    }
}
