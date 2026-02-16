package com.household.finance.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegistrationData(@NotBlank String email,
                                   @NotBlank String password,
                                   @NotBlank String confirmationPassword,
                                   @NotBlank String name,
                                   @NotBlank String nickname) {
}
