package com.ghoul.weather.services;

import com.ghoul.weather.client.IpApiClient;
import com.ghoul.weather.model.dto.Geolocation;
import com.ghoul.weather.exceptions.InvalidExternalResponseException;
import org.springframework.stereotype.Service;

@Service
public class GeolocationService {
    private final IpApiClient ipApiClient;

    public GeolocationService(IpApiClient ipApiClient) {
        this.ipApiClient = ipApiClient;
    }

    public Geolocation getLocation(String ip) {
        var location = ipApiClient.getUserLocation(ip);
        if (location == null || !Double.isFinite(location.getLat()) || !Double.isFinite(location.getLon())
                || location.getLat() < -90 || location.getLat() > 90 || location.getLon() < -180 || location.getLon() > 180
                || location.getCity() == null || location.getCity().isBlank()
                || location.getCountryCode() == null || !location.getCountryCode().matches("[A-Z]{2}")) {
            throw new InvalidExternalResponseException("Geolocation provider returned incomplete location data");
        }
        return new Geolocation(
                location.getLat(),
                location.getLon(),
                location.getCity(),
                location.getCountryCode()
        );
    }
}
