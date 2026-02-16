package com.household.finance.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginData(@NotBlank String email, @NotBlank String password) {
}
