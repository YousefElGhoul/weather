package com.ghoul.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(OwmProperties.class)
public class RestClientConfig {

    @Value("${owm.base-url}")
    private String owmBaseUrl;

    @Value("${ipapi.base-url}")
    private String ipapiBaseUrl;

    @Value("${external-api.connect-timeout:3s}")
    private Duration connectTimeout;

    @Value("${external-api.read-timeout:5s}")
    private Duration readTimeout;

    @Bean
    public RestClient owmRestClient() {
        return getRestClient(owmBaseUrl);
    }

    @Bean
    public RestClient ipapiRestClient() {
        return getRestClient(ipapiBaseUrl);
    }

    private RestClient getRestClient(String baseUrl) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(requestFactory)
                .build();
    }
}
