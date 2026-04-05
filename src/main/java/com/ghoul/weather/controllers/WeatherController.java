package com.ghoul.weather.controllers;

import com.ghoul.weather.model.dto.MiniWeatherResponse;
import com.ghoul.weather.model.dto.WeatherResponse;
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
        String forwardedFor = request.getHeader("X-Forwarded-For");
        String remoteAddr = request.getRemoteAddr();

        System.out.println("X-Forwarded-For: " + forwardedFor);
        System.out.println("RemoteAddr: " + remoteAddr);

        String ip = forwardedFor != null ? forwardedFor : remoteAddr;
        return weatherService.getMiniWeather(ip);
    }

    @ResponseBody
    @GetMapping(path = "/weather")
    public WeatherResponse getWeather(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        String remoteAddr = request.getRemoteAddr();

        System.out.println("X-Forwarded-For: " + forwardedFor);
        System.out.println("RemoteAddr: " + remoteAddr);

        String ip = forwardedFor != null ? forwardedFor : remoteAddr;
        return weatherService.getWeather(ip);
    }

    @ResponseBody
    @GetMapping(path = "/")
    public String test() {
        return "This is a Test";
    }
}
