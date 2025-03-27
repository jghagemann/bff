package com.example.bff.clients;

import com.example.bff.dto.AccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class LegacyAccountsClient {

    private final WebClient webClient;

    public LegacyAccountsClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        return webClient.get()
                .uri("/accounts")
                .retrieve()
                .toEntityList(AccountDto.class).block();
    }

    public ResponseEntity<AccountDto> getOne(String id) {
        return this.webClient.get()
                .uri("/accounts/{id}", id)
                .retrieve()
                .toEntity(AccountDto.class).block();
    }
}
