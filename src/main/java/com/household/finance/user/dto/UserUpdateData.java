package com.household.finance.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateData(@NotBlank String name,
                             @NotBlank String nickname) {
}
