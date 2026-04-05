package com.ghoul.weather.client;

import com.ghoul.weather.model.external.ipapi.IpApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class IpApiClient {
    private final RestClient restClient;

    public IpApiClient(@Qualifier("ipapiRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public IpApiResponse getUserLocation(String ip) {
        if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "156.223.172.35"; // fallback test IP for local dev
        }
        System.out.println("Fetching location for IP: " + ip);
        return restClient.get()
                .uri("/{ip}?fields=status,message,countryCode,city,lat,lon", ip)
                .retrieve()
                .body(IpApiResponse.class);
    }
}

