package com.project.carsharingapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health management",
        description = "Send message if application is running")
@RestController
@RequestMapping(value = "/health")
public class HealthController {
    @GetMapping
    public String checkHealth() {
        return "Application is up and running";
    }
}
