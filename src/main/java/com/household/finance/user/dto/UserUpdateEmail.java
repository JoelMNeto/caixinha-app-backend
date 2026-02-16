package com.household.finance.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateEmail(@NotBlank String newEmail) {
}
