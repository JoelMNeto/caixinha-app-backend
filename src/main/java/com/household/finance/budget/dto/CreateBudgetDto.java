package com.household.finance.budget.dto;

public record CreateBudgetDto(
        Long categoryId,
        String amount,
        String period,
        Integer budgetMonth,
        Integer budgetYear) {
}
