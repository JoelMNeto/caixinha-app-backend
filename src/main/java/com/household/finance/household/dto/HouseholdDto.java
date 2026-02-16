package com.household.finance.household.dto;

import com.household.finance.household.entity.Household;

import java.time.LocalDateTime;

public record HouseholdDto(Long id,
                           String name,
                           Long createdBy,
                           LocalDateTime createdAt) {

    public HouseholdDto(Household household) {
        this(household.getId(), household.getName(), household.getCreatedBy().getId(), household.getCreatedAt());
    }
}
