package com.ghoul.weather.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WeatherController {



    @ResponseBody
    @GetMapping("/test")
    public String sayHello() {
        return "Hello World!";
    }
}
