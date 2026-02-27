package com.household.finance.recurring.dto;

import java.time.LocalDateTime;

public record CreateRecurringDto(Long householdId,
                                 Long categoryId,
                                 String type,
                                 String amount,
                                 String description,
                                 String paymentMethod,
                                 String frequency,
                                 LocalDateTime startDate,
                                 LocalDateTime endDate) {
}
