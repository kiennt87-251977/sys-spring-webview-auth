package com.auth.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public ServiceCacheConfig serviceCacheConfig() {
        return new ServiceCacheConfig();
    }
}
