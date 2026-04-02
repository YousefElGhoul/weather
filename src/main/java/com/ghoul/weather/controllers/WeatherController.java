package com.ghoul.weather.controllers;

import com.ghoul.weather.client.IpApiClient;
import com.ghoul.weather.model.dto.MiniWeatherResponse;
import com.ghoul.weather.model.dto.WeatherResponse;
import com.ghoul.weather.model.external.ipapi.IpApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {

    private final IpApiClient ipApiClient;

    public WeatherController(IpApiClient ipApiClient) {
        this.ipApiClient = ipApiClient;
    }

    @ResponseBody
    @GetMapping(path = "/mini-weather")
    public MiniWeatherResponse getMiniWeather() {
        return new MiniWeatherResponse("Clear Sky", 25.0);
    }

    @ResponseBody
    @GetMapping(path = "/weather")
    public WeatherResponse getWeather() {
        return new WeatherResponse(null, null);
    }

    @ResponseBody
    @GetMapping(path = "/test")
    public IpApiResponse sayHello(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ipApiClient.getLocation(ip);
    }
}
