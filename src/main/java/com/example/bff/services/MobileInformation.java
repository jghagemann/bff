package com.example.bff.services;

import com.example.bff.dto.MobileProfileDto;
import com.example.bff.dto.MobileSimpleProfileDto;
import jakarta.validation.constraints.NotBlank;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MobileInformation {

    Mono<MobileProfileDto> getMobileProfileById(@NotBlank String id);
    Flux<MobileSimpleProfileDto> getSimpleMobileProfileList();

}
