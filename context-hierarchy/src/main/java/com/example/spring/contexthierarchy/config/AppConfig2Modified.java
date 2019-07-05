package com.example.spring.contexthierarchy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig2Modified {
    @Bean
    public String stringTwo() {
        System.err.println("Creating stringTwoModified");
        return "stringTwoModified";
    }
}
