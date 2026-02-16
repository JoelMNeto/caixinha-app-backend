package com.household.finance.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenData(@NotBlank String refreshToken) {
}
