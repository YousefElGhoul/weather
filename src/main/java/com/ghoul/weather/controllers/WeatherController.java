package com.ghoul.weather.controllers;

import com.ghoul.weather.model.dto.Geolocation;
import com.ghoul.weather.model.dto.MiniWeatherResponse;
import com.ghoul.weather.model.dto.WeatherResponse;
import com.ghoul.weather.services.GeolocationService;
import com.ghoul.weather.services.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @ResponseBody
    @GetMapping(path = "/mini-weather")
    public MiniWeatherResponse getMiniWeather(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return new MiniWeatherResponse("Clear Sky", 25.0);
    }

    @ResponseBody
    @GetMapping(path = "/weather")
    public WeatherResponse getWeather(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return weatherService.getWeather(ip);
    }

    @ResponseBody
    @GetMapping(path = "/test")
    public String test() {
        return "This is a Test";
    }
}
