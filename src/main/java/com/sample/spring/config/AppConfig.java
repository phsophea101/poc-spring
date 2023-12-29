package com.sample.spring.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    ObjectMapper mapper() {
        return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
