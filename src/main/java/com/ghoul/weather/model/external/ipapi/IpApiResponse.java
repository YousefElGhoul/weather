package com.ghoul.weather.model.external.ipapi;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IpApiResponse {
    private String status;
    private String countryCode;
    private String city;
    private double lat;
    private double lon;
}
