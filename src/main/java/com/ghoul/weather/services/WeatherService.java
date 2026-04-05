package com.ghoul.weather.services;

import com.ghoul.weather.client.OwmClient;
import com.ghoul.weather.model.dto.*;
import com.ghoul.weather.model.external.owm.OWMResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {
    private final OwmClient owmClient;
    private final GeolocationService geolocationService;

    public WeatherService(OwmClient owmClient, GeolocationService geolocationService) {
        this.owmClient = owmClient;
        this.geolocationService = geolocationService;
    }

    private TodayWeather getCurrentWeather(OWMResponse response) {
        return new TodayWeather(
                response.getCurrent().getWeather().getFirst().getIcon(),
                response.getCurrent().getWeather().getFirst().getDescription(),
                new Temperature(
                        response.getCurrent().getTemp(),
                        response.getCurrent().getFeelsLike(),
                        response.getDaily().getFirst().getTemp().getLow(),
                        response.getDaily().getFirst().getTemp().getHigh()
                ),
                response.getDaily().getFirst().getTemp().getMidnight(),
                response.getDaily().getFirst().getTemp().getMorning(),
                response.getDaily().getFirst().getTemp().getNoon(),
                response.getDaily().getFirst().getTemp().getEvening()
                );
    }

    private List<ForecastWeather> getForecast(OWMResponse response) {
        List<ForecastWeather> forecast = new ArrayList<>();

        for (int i = 1; i <= 7; i++) {
            forecast.add(new ForecastWeather(
                    response.getDaily().get(i).getWeather().getFirst().getIcon(),
                    response.getDaily().get(i).getWeather().getFirst().getDescription(),
                    new Temperature(
                            response.getDaily().get(i).getTemp().getHigh(),
                            response.getDaily().get(i).getFeelsLike().getEvening(),
                            response.getDaily().get(i).getTemp().getLow(),
                            response.getDaily().get(i).getTemp().getHigh()
                    )
            ));
        }

        return forecast;
    }

    public WeatherResponse getWeather(String ip) {
        Geolocation geolocation = geolocationService.getLocation(ip);
        OWMResponse response =  owmClient.getOwmResponse(geolocation);

        return new WeatherResponse(
                geolocation.city(),
                geolocation.countryCode(),
                getCurrentWeather(response),
                getForecast(response)
        );
    }

}
