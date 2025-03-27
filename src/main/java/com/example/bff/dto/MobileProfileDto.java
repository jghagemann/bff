package com.example.bff.dto;

import jakarta.validation.constraints.NotBlank;

public record MobileProfileDto(

        @NotBlank String id,
        @NotBlank String name,
        @NotBlank String avatar,
        @NotBlank String bio,
        @NotBlank String companyName,
        @NotBlank String jobTitle,
        @NotBlank String accountId,
        @NotBlank String currency,
        @NotBlank String balance
) {
}
