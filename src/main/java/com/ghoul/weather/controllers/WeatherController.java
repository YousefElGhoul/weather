package com.ghoul.weather.controllers;

import com.ghoul.weather.model.dto.MiniWeatherResponse;
import com.ghoul.weather.model.dto.WeatherResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {

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
    public String sayHello() {
        return "Hello World!\n ";
    }
}
