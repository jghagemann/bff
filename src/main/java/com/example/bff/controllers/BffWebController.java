package com.example.bff.controllers;

import com.example.bff.dto.WebProfileDto;
import com.example.bff.services.WebInformation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/web/profiles")
public class BffWebController {

    private final WebInformation webService;

    public BffWebController(WebInformation webService) {
        this.webService = webService;
    }

    @GetMapping("/{id}")
    public Mono<WebProfileDto> getProfileById(@PathVariable String id) {
        return webService.getWebProfileById(id);
    }
}
