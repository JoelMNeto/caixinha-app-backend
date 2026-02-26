package com.household.finance.transaction.dto;

public record UpdateTransactionDto(Long householdId,
                                   Long categoryId,
                                   String type,
                                   String amount,
                                   String description,
                                   String transactionDate,
                                   String paymentMethod) {
}
