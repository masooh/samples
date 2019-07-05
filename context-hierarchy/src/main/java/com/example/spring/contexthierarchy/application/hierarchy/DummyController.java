package com.example.spring.contexthierarchy.application.hierarchy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @GetMapping("/")
    public String root() {
        return "root";
    }
}
