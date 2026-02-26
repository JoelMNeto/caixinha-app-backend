package com.household.finance.household.dto;

import jakarta.validation.constraints.NotBlank;

public record HouseholdRegistrationData(@NotBlank String name) {
}
