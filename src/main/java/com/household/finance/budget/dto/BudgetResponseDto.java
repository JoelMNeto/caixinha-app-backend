package com.household.finance.budget.dto;

import com.household.finance.budget.entity.Budget;
import com.household.finance.category.dto.CategoryResponseDto;
import com.household.finance.household.dto.HouseholdDto;

import java.time.LocalDateTime;

public record BudgetResponseDto(Long id,
                                HouseholdDto householdDto,
                                CategoryResponseDto categoryResponseDto,
                                String amount,
                                String period,
                                Integer budgetMonth,
                                Integer budgetYear,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt
                                ) {

    public BudgetResponseDto(Budget budget) {
        this(budget.getId(),
                new HouseholdDto(budget.getHousehold()),
                new CategoryResponseDto(budget.getCategory()),
                budget.getAmount().toString(),
                budget.getPeriod().name(),
                budget.getBudgetMonth(),
                budget.getBudgetYear(),
                budget.getCreatedAt(),
                budget.getUpdatedAt());
    }
}
