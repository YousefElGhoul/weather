package com.ghoul.weather.client;

import com.ghoul.weather.model.external.ipapi.IpApiResponse;
import com.ghoul.weather.exceptions.UpstreamServiceException;
import com.ghoul.weather.exceptions.UpstreamTimeoutException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.net.SocketTimeoutException;

@Component
public class IpApiClient {
    private final RestClient restClient;

    public IpApiClient(@Qualifier("ipapiRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public IpApiResponse getUserLocation(String ip) {
        try {
            IpApiResponse response = restClient.get()
                    .uri("/{ip}?fields=status,message,countryCode,city,lat,lon", ip)
                    .retrieve()
                    .body(IpApiResponse.class);
            if (response == null || !"success".equalsIgnoreCase(response.getStatus())) {
                throw new UpstreamServiceException("Geolocation provider returned no usable location", null);
            }
            return response;
        } catch (ResourceAccessException ex) {
            if (ex.getCause() instanceof SocketTimeoutException) {
                throw new UpstreamTimeoutException("Geolocation provider timed out", ex);
            }
            throw new UpstreamServiceException("Geolocation provider is unreachable", ex);
        } catch (RestClientResponseException ex) {
            throw new UpstreamServiceException("Geolocation provider returned HTTP " + ex.getStatusCode(), ex);
        }
    }
}
