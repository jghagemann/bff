package com.example.bff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record AccountDto(

        @NotBlank String id,
        @NotBlank @JsonProperty("currency") String currency,
        @NotBlank @JsonProperty("number") String number,
        @NotBlank @JsonProperty("balance") BigDecimal balance
) {
}
