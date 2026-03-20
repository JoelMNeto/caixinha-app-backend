package com.household.finance.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserResetPasswordData(@NotBlank String confirmationCode,
                                    @NotBlank String newPassword,
                                    @NotBlank String confirmationNewPassword) {
}
