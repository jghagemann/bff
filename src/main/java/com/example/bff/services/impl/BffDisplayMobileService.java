package com.example.bff.services.impl;

import com.example.bff.clients.ExternalApiClient;
import com.example.bff.clients.LegacyAccountsClient;
import com.example.bff.dto.AccountDto;
import com.example.bff.dto.MobileProfileDto;
import com.example.bff.dto.MobileSimpleProfileDto;
import com.example.bff.services.MobileInformation;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
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

        return apiClient.getOne(id)
                .map(profileDto -> new MobileProfileDto(profileDto.id(), profileDto.name(), profileDto.avatar(),
                        profileDto.bio(), profileDto.companyName(), profileDto.jobTitle(), accountDto.id(),
                        accountDto.currency(), accountDto.balance().toString()));
    }

    @Override
    public Flux<MobileSimpleProfileDto> getSimpleMobileProfileList() {
        return  apiClient.getAllProfiles().map(profileDto -> new MobileSimpleProfileDto(profileDto.id(), profileDto.name(), profileDto.avatar(),
                profileDto.bio(), profileDto.companyName(), profileDto.jobTitle()));
    }
}
