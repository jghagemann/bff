package com.example.bff.services.impl;

import com.example.bff.clients.ExternalApiClient;
import com.example.bff.clients.LegacyAccountsClient;
import com.example.bff.dto.WebProfileDto;
import com.example.bff.services.WebInformation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class BffDisplayWebService implements WebInformation {

    private final ExternalApiClient apiClient;

    private final LegacyAccountsClient legacyAccountsClient;


    public BffDisplayWebService(ExternalApiClient apiClient, LegacyAccountsClient legacyAccountsClient) {
        this.apiClient = apiClient;
        this.legacyAccountsClient = legacyAccountsClient;
    }

    @Override
    public Mono<WebProfileDto> getWebProfileById(String id) {

        return Mono.fromCallable(() -> legacyAccountsClient.getOne(id).getBody())
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(accountDto ->
                        apiClient.getOne(id).map(profileDto ->
                                new WebProfileDto(profileDto.id(), profileDto.name(), profileDto.avatar(),
                                        profileDto.bio(), profileDto.companyName(), profileDto.jobTitle(),
                                        profileDto.jobDescriptor(), profileDto.jobType(), profileDto.createdAt(),
                                        accountDto.id(), accountDto.currency(), accountDto.number(),
                                        accountDto.balance().toString()
                                )
                        )
                );
    }

}
