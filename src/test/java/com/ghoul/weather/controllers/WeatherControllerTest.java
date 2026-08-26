package com.ghoul.weather.controllers;

import com.ghoul.weather.exceptions.GlobalExceptionHandler;
import com.ghoul.weather.exceptions.UpstreamTimeoutException;
import com.ghoul.weather.model.dto.MiniWeatherResponse;
import com.ghoul.weather.services.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WeatherControllerTest {
    private final WeatherService weatherService = mock(WeatherService.class);
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new WeatherController(weatherService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void returnsMiniWeatherForValidClientAddress() throws Exception {
        when(weatherService.getMiniWeather("203.0.113.4")).thenReturn(new MiniWeatherResponse("Clear Sky", 25.0));

        mockMvc.perform(get("/api/v1/mini-weather").with(request -> { request.setRemoteAddr("203.0.113.4"); return request; }))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description").value("Clear Sky"))
                .andExpect(jsonPath("$.temperature").value(25.0));
    }

    @Test
    void returnsConsistentErrorWhenProviderTimesOut() throws Exception {
        when(weatherService.getMiniWeather(anyString())).thenThrow(new UpstreamTimeoutException("timeout", null));

        mockMvc.perform(get("/api/v1/mini-weather").with(request -> { request.setRemoteAddr("203.0.113.4"); return request; }))
                .andExpect(status().isGatewayTimeout())
                .andExpect(jsonPath("$.status").value(504))
                .andExpect(jsonPath("$.error").value("Upstream timeout"))
                .andExpect(jsonPath("$.path").value("/api/v1/mini-weather"));
    }

    @Test
    void rejectsMissingClientAddress() throws Exception {
        mockMvc.perform(get("/api/v1/mini-weather").with(request -> { request.setRemoteAddr("unknown"); return request; }))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
        verifyNoInteractions(weatherService);
    }
}
