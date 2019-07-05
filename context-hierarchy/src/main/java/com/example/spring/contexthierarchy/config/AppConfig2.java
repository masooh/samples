package com.example.spring.contexthierarchy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig2 {
    @Bean
    public String stringTwo() throws InterruptedException {
        System.err.println("Creating stringTwo");
        return "stringTwo";
    }
}
