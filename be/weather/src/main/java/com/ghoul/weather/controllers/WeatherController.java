package com.ghoul.weather.controllers;

import com.ghoul.weather.config.CloudflareIpRanges;
import com.ghoul.weather.model.dto.MiniWeatherResponse;
import com.ghoul.weather.model.dto.WeatherResponse;
import com.ghoul.weather.services.WeatherService;
import com.ghoul.weather.exceptions.InvalidClientDataException;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1")
public class WeatherController {

    private static final String CF_CONNECTING_IP = "CF-Connecting-IP";

    private final WeatherService weatherService;
    private final CloudflareIpRanges cloudflareIpRanges;

    public WeatherController(WeatherService weatherService, CloudflareIpRanges cloudflareIpRanges) {
        this.weatherService = weatherService;
        this.cloudflareIpRanges = cloudflareIpRanges;
    }

    @GetMapping(path = "/mini-weather")
    @Operation(summary = "Get current weather for the caller's approximate IP location")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Current weather returned"),
            @ApiResponse(responseCode = "400", description = "Client address is invalid"),
            @ApiResponse(responseCode = "502", description = "An upstream provider failed"),
            @ApiResponse(responseCode = "504", description = "An upstream provider timed out")})
    public MiniWeatherResponse getMiniWeather(HttpServletRequest request) {
        return weatherService.getMiniWeather(resolveClientIp(request));
    }

    @GetMapping(path = "/full-weather")
    @Operation(summary = "Get today and six future forecast days for the caller's approximate IP location")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Forecast returned"),
            @ApiResponse(responseCode = "400", description = "Client address is invalid"),
            @ApiResponse(responseCode = "502", description = "An upstream provider failed"),
            @ApiResponse(responseCode = "504", description = "An upstream provider timed out")})
    public WeatherResponse getWeather(HttpServletRequest request) {
        return weatherService.getWeather(resolveClientIp(request));
    }

    private String resolveClientIp(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        // Behind Cloudflare the resolved address is a Cloudflare edge server, so use the
        // visitor's address Cloudflare forwards. Only trusted when the request really came from Cloudflare.
        String cloudflareClientIp = request.getHeader(CF_CONNECTING_IP);
        if (cloudflareClientIp != null && !cloudflareClientIp.isBlank() && cloudflareIpRanges.contains(ip)) {
            ip = cloudflareClientIp.trim();
        }
        if (CloudflareIpRanges.parseLiteral(ip) == null) {
            throw new InvalidClientDataException("A valid client IP address is required.");
        }
        return ip;
    }
}
