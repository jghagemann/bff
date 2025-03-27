package com.example.bff.dto;

import jakarta.validation.constraints.NotBlank;

public record MobileSimpleProfileDto(

        @NotBlank String id,
        @NotBlank String name,
        @NotBlank String avatar,
        @NotBlank String bio,
        @NotBlank String companyName,
        @NotBlank String jobTitle
) {
}
