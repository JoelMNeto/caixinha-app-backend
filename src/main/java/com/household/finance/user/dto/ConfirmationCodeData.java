package com.household.finance.user.dto;

import jakarta.validation.constraints.NotBlank;

public record ConfirmationCodeData(@NotBlank String confirmationCode) {
}
