package com.ghoul.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${owm.base-url}")
    private String owmBaseUrl;

    @Value("${ipapi.base-url}")
    private String ipapiBaseUrl;

    @Bean
    public RestClient owmRestClient() {
        return RestClient.builder()
                .baseUrl(owmBaseUrl)
                .build();
    }

    @Bean
    public RestClient ipapiRestClient() {
        return RestClient.builder()
                .baseUrl(ipapiBaseUrl)
                .build();
    }
}