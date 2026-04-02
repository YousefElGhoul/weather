package com.ghoul.weather.config;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${owm.base-url}")
    private String owmBaseUrl;

    @Value("${ipapi.base-url}")
    private String ipapiBaseUrl;

    @Bean
    public RestClient owmRestClient() {
        return getRestClient(owmBaseUrl);
    }

    @Bean
    public RestClient ipapiRestClient() {
        return getRestClient(ipapiBaseUrl);
    }

    @NonNull
    private RestClient getRestClient(String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {
                    System.out.println(">>> Method : " + request.getMethod());
                    System.out.println(">>> URL    : " + request.getURI());
                    System.out.println(">>> Headers: " + request.getHeaders());

                    ClientHttpResponse response = execution.execute(request, body);

                    System.out.println("<<< Status : " + response.getStatusCode());
                    return response;
                })
                .build();
    }
}