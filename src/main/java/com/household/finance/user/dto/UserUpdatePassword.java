package com.household.finance.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdatePassword(@NotBlank String currentPassword,
                                 @NotBlank String newPassword,
                                 @NotBlank String confirmationNewPassword) {
}
