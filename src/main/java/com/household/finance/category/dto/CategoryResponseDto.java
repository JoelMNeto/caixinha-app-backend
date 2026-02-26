package com.household.finance.category.dto;

import com.household.finance.category.entity.Category;

import java.time.LocalDateTime;

public record CategoryResponseDto(Long id,
                                  Long householdId,
                                  String name,
                                  String type,
                                  String color,
                                  String icon,
                                  boolean isDefault,
                                  LocalDateTime createdAt) {

    public CategoryResponseDto(Category category) {
        this(category.getId(),
             category.getHousehold().getId(),
             category.getName(),
             category.getType().name(),
             category.getColor(),
             category.getIcon(),
             category.isDefault(),
             category.getCreatedAt());
    }
}
