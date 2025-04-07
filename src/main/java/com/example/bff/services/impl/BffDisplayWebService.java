package com.example.bff.services.impl;

import com.example.bff.clients.ExternalApiClient;
import com.example.bff.clients.LegacyAccountsClient;
import com.example.bff.dto.AccountDto;
import com.example.bff.dto.WebProfileDto;
import com.example.bff.services.WebInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@Slf4j
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
                .timeout(Duration.ofSeconds(3))
                .onErrorResume(ex -> {
                    log.warn("Failed to fetch legacy account data for id {}: {}", id, ex.toString());
                    return Mono.just(new AccountDto("N/A", "N/A", "0000", BigDecimal.ZERO));
                })
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
