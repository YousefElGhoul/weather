package com.ghoul.weather.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.ghoul.weather.model.dto.Geolocation;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager("weatherForecast");
        manager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(10))
                .maximumSize(1_000));
        return manager;
    }

    @Bean("weatherCacheKeyGenerator")
    KeyGenerator weatherCacheKeyGenerator() {
        return (Object target, Method method, Object... params) -> {
            Geolocation location = (Geolocation) params[0];
            // The key contains only rounded coordinates; a caller IP is never cached.
            return String.format(java.util.Locale.ROOT, "%.3f:%.3f", location.lat(), location.lon());
        };
    }
}
