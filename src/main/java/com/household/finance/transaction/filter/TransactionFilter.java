package com.household.finance.transaction.filter;

import com.household.finance.category.enumerations.TypeEnum;

import java.time.LocalDateTime;

public record TransactionFilter(Long householdId,
                                Long categoryId,
                                Long userId,
                                TypeEnum type,
                                LocalDateTime startDate,
                                LocalDateTime endDate) {
}
