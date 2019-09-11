package com.example.spring.contexthierarchy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigOne {

    @Bean
    public String one() {
        return "one";
    }
}
