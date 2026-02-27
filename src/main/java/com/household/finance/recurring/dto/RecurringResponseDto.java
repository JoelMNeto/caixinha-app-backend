package com.household.finance.recurring.dto;

import com.household.finance.category.dto.CategoryResponseDto;
import com.household.finance.household.dto.HouseholdDto;
import com.household.finance.recurring.entity.RecurringTransaction;
import com.household.finance.user.dto.UserResponseData;

import java.time.LocalDateTime;

public record RecurringResponseDto(Long id,
                                   HouseholdDto household,
                                   UserResponseData user,
                                   CategoryResponseDto category,
                                   String type,
                                   String amount,
                                   String description,
                                   String frequency,
                                   String startDate,
                                   String endDate,
                                   String paymentMethod,
                                   LocalDateTime createdAt,
                                   LocalDateTime updatedAt) {

    public RecurringResponseDto(RecurringTransaction recurringTransaction) {
        this(
                recurringTransaction.getId(),
                new HouseholdDto(recurringTransaction.getHousehold()),
                new UserResponseData(recurringTransaction.getUser()),
                new CategoryResponseDto(recurringTransaction.getCategory()),
                recurringTransaction.getType().name(),
                recurringTransaction.getAmount().toString(),
                recurringTransaction.getDescription(),
                recurringTransaction.getFrequency().name(),
                recurringTransaction.getStartDate().toString(),
                recurringTransaction.getEndDate() != null ? recurringTransaction.getEndDate().toString() : null,
                recurringTransaction.getPaymentMethod(),
                recurringTransaction.getCreatedAt(),
                recurringTransaction.getUpdatedAt()
        );
    }
}
