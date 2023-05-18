package com.lastone.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class HealthCheckController {
    @GetMapping("/health")
    public void getHealthCheck() {}
}
