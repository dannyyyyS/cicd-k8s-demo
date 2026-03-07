package org.example.cicd.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.cicd.AppProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SimpleController {

    private final AppProperties appProperties;

    @GetMapping("/hello")
    public String helloWorld() {
        return String.format("Hello %s!", appProperties.getGreeting());
    }
}
