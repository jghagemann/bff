package com.example.bff.clients;

import com.example.bff.dto.ProfileDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExternalApiClient {

    private final WebClient webClient;

    public ExternalApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<ProfileDto> getAllProfiles() {
        return webClient.get()
                .uri("/profiles")
                .retrieve()
                .bodyToFlux(ProfileDto.class);
    }

    public Mono<ProfileDto> getOne(String id) {
        return this.webClient.get()
                .uri("/profiles/{id}", id)
                .retrieve()
                .bodyToMono(ProfileDto.class);
    }
}
