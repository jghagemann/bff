package com.example.bff.services;

import com.example.bff.dto.WebProfileDto;
import reactor.core.publisher.Mono;

public interface WebInformation {

    Mono<WebProfileDto> getWebProfileById(String id);
}
