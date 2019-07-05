package com.example.spring.contexthierarchy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig1 {

    @Bean
    public String stringOne() throws InterruptedException {
        System.err.println("Creating stringOne");
        return "stringOne";
    }
}
