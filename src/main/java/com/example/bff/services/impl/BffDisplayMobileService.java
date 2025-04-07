package com.example.bff.services.impl;

import com.example.bff.clients.ExternalApiClient;
import com.example.bff.clients.LegacyAccountsClient;
import com.example.bff.dto.AccountDto;
import com.example.bff.dto.MobileProfileDto;
import com.example.bff.dto.MobileSimpleProfileDto;
import com.example.bff.services.MobileInformation;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@Slf4j
public class BffDisplayMobileService implements MobileInformation {

    private final ExternalApiClient apiClient;

    private final LegacyAccountsClient legacyAccountsClient;

    public BffDisplayMobileService(ExternalApiClient apiClient, LegacyAccountsClient legacyAccountsClient) {
        this.apiClient = apiClient;
        this.legacyAccountsClient = legacyAccountsClient;
    }

    @Override
    public Mono<MobileProfileDto> getMobileProfileById(@NotBlank String id) {

        ResponseEntity<AccountDto> accountInfo = legacyAccountsClient.getOne(id);
        AccountDto accountDto = accountInfo.getBody();

        return Mono.fromCallable(() -> legacyAccountsClient.getOne(id).getBody())
                .subscribeOn(Schedulers.boundedElastic())
                .timeout(Duration.ofSeconds(3))
                .onErrorResume(ex -> {
                    log.warn("Failed to fetch legacy account data for id {}: {}", id, ex.toString());
                    return Mono.just(new AccountDto("N/A", "N/A", "0000", BigDecimal.ZERO));
                })
                .flatMap(accountDto1 ->
                        apiClient.getOne(id).map(profileDto ->
                                new MobileProfileDto(profileDto.id(), profileDto.name(), profileDto.avatar(),
                                        profileDto.bio(), profileDto.companyName(), profileDto.jobTitle(), accountDto.id(),
                                        accountDto.currency(), accountDto.balance().toString())));

    }

    @Override
    public Flux<MobileSimpleProfileDto> getSimpleMobileProfileList() {
        return  apiClient.getAllProfiles().map(profileDto -> new MobileSimpleProfileDto(profileDto.id(), profileDto.name(), profileDto.avatar(),
                profileDto.bio(), profileDto.companyName(), profileDto.jobTitle()));
    }
}
