package com.household.finance.transaction.dto;

import java.time.LocalDateTime;

public record CreateTransactionDto(Long householdId,
                                   Long categoryId,
                                   String type,
                                   String amount,
                                   String description,
                                   LocalDateTime transactionDate,
                                   String paymentMethod) {
}
