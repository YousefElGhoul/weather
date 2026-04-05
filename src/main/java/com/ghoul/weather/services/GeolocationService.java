package com.ghoul.weather.services;

import com.ghoul.weather.client.IpApiClient;
import com.ghoul.weather.model.dto.Geolocation;
import org.springframework.stereotype.Service;

@Service
public class GeolocationService {
    private final IpApiClient ipApiClient;

    public GeolocationService(IpApiClient ipApiClient) {
        this.ipApiClient = ipApiClient;
    }

    public Geolocation getLocation(String ip) {
        var location = ipApiClient.getUserLocation(ip);
        return new Geolocation(
                location.getLat(),
                location.getLon(),
                location.getCity(),
                location.getCountryCode()
        );
    }
}
