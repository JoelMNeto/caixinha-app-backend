package com.household.finance.category.dto;

public record CreateCategoryDto(Long householdId,
                                String name,
                                String type,
                                String icon,
                                String color) {
}
