package com.example.bff.controllers;

import com.example.bff.dto.MobileProfileDto;
import com.example.bff.dto.MobileSimpleProfileDto;
import com.example.bff.services.MobileInformation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mobile/profiles")
public class BffMobileController {

    private final MobileInformation mobileService;

    public BffMobileController(MobileInformation mobileService) {
        this.mobileService = mobileService;
    }

    @GetMapping
    public Flux<MobileSimpleProfileDto> getAllSimpleProfiles() {
        return mobileService.getSimpleMobileProfileList();
    }

    @GetMapping("/{id}")
    public Mono<MobileProfileDto> getProfileById(@PathVariable @NotBlank String id) {
        return mobileService.getMobileProfileById(id);
    }
}
