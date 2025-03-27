package com.example.bff.dto;

import jakarta.validation.constraints.NotBlank;

public record WebProfileDto (

    @NotBlank String id,
    @NotBlank String name,
    @NotBlank String avatar,
    @NotBlank String bio,
    @NotBlank String companyName,
    @NotBlank String jobTitle,
    @NotBlank String jobDescriptor,
    @NotBlank String jobType,
    @NotBlank String createdAt,
    @NotBlank String accountId,
    @NotBlank String currency,
    @NotBlank String number,
    @NotBlank String balance
) {
}