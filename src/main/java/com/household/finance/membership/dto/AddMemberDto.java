package com.household.finance.membership.dto;

import jakarta.validation.constraints.NotBlank;

public record AddMemberDto(@NotBlank Long userId, @NotBlank Long householdId) {
}
