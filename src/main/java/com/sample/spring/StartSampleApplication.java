package com.sample.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.sample.spring.entity")
public class StartSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartSampleApplication.class, args);
    }
}
