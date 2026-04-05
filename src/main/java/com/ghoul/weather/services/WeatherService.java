package com.ghoul.weather.services;

import com.ghoul.weather.client.OwmClient;
import com.ghoul.weather.mappers.DescriptionMapper;
import com.ghoul.weather.mappers.FlagMapper;
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
                DescriptionMapper.getConditionIcon(response.getCurrent().getWeather().getFirst().getIcon()),
                DescriptionMapper.formatDescription(response.getCurrent().getWeather().getFirst().getDescription()),
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

        forecast.add(null);

        for (int i = 1; i <= 6; i++) {
            forecast.add(new ForecastWeather(
                    DescriptionMapper.getConditionIcon(response.getDaily().get(i).getWeather().getFirst().getIcon()),
                    DescriptionMapper.formatDescription(response.getDaily().get(i).getWeather().getFirst().getDescription()),
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
                FlagMapper.getFlag(geolocation.countryCode()),
                getCurrentWeather(response),
                getForecast(response)
        );
    }

    public MiniWeatherResponse getMiniWeather(String ip) {
        Geolocation geolocation = geolocationService.getLocation(ip);
        OWMResponse response =  owmClient.getOwmResponse(geolocation);

        return new MiniWeatherResponse(
                DescriptionMapper.formatDescription(response.getCurrent().getWeather().getFirst().getDescription()),
                response.getCurrent().getTemp()
        );
    }

}
