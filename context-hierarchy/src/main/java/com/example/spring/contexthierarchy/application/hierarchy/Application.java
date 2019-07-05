package com.example.spring.contexthierarchy.application.hierarchy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public String stringApp() throws InterruptedException {
        System.err.println("Creating stringApp");
        return "stringApp";
    }

}
