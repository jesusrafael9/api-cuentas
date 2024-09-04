package com.devsu.cuentas.config;

import org.springframework.cloud.openfeign.Targeter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public Targeter targeter() {
        return new CustomTargeter();
    }
}