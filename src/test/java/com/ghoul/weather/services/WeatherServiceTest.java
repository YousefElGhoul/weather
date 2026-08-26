package com.ghoul.weather.services;

import com.ghoul.weather.client.OwmClient;
import com.ghoul.weather.model.dto.Geolocation;
import com.ghoul.weather.model.dto.WeatherResponse;
import com.ghoul.weather.model.external.owm.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WeatherServiceTest {
    private final OwmClient owmClient = mock(OwmClient.class);
    private final GeolocationService geolocationService = mock(GeolocationService.class);
    private final WeatherService service = new WeatherService(owmClient, geolocationService);

    @Test
    void returnsSixForecastDaysWithoutNullEntries() {
        Geolocation location = new Geolocation(30.0444, 31.2357, "Cairo", "EG");
        when(geolocationService.getLocation("203.0.113.4")).thenReturn(location);
        when(owmClient.getOwmResponse(location)).thenReturn(weatherResponse());

        WeatherResponse result = service.getWeather("203.0.113.4");

        assertThat(result.city()).isEqualTo("Cairo");
        assertThat(result.forecastWeather()).hasSize(6).doesNotContainNull();
        assertThat(result.todayWeather().description()).isEqualTo("Clear Sky");
    }

    @Test
    void returnsCurrentConditionsForMiniWeather() {
        Geolocation location = new Geolocation(30.0444, 31.2357, "Cairo", "EG");
        when(geolocationService.getLocation(anyString())).thenReturn(location);
        when(owmClient.getOwmResponse(location)).thenReturn(weatherResponse());

        var result = service.getMiniWeather("203.0.113.4");

        assertThat(result.description()).isEqualTo("Clear Sky");
        assertThat(result.temperature()).isEqualTo(25.0);
    }

    private OWMResponse weatherResponse() {
        OWMWeatherDescription condition = new OWMWeatherDescription();
        condition.setDescription("clear sky");
        condition.setIcon("01d");

        OWMCurrentWeather current = new OWMCurrentWeather();
        current.setTemp(25.0); current.setFeelsLike(24.0); current.setWeather(List.of(condition));
        OWMResponse response = new OWMResponse();
        response.setCurrent(current);
        response.setDaily(IntStream.range(0, 7).mapToObj(index -> daily(condition)).toList());
        return response;
    }

    private OWMDailyItem daily(OWMWeatherDescription condition) {
        OWMDailyTemp temp = new OWMDailyTemp();
        temp.setLow(20.0); temp.setHigh(30.0); temp.setMidnight(21.0);
        temp.setMorning(22.0); temp.setNoon(29.0); temp.setEvening(26.0);
        OWMDailyFeelsLike feels = new OWMDailyFeelsLike(); feels.setEvening(25.0);
        OWMDailyItem item = new OWMDailyItem();
        item.setTemp(temp); item.setFeelsLike(feels); item.setWeather(List.of(condition));
        return item;
    }
}
