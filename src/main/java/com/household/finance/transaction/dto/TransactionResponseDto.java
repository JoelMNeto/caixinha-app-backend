package com.household.finance.transaction.dto;

import com.household.finance.category.dto.CategoryResponseDto;
import com.household.finance.household.dto.HouseholdDto;
import com.household.finance.transaction.entity.Transaction;
import com.household.finance.user.dto.UserResponseData;

public record TransactionResponseDto(Long id,
                                     HouseholdDto householdId,
                                     UserResponseData userId,
                                     CategoryResponseDto categoryId,
                                     String type,
                                     String amount,
                                     String description,
                                     String transactionDate,
                                     String paymentMethod,
                                     Boolean isRecurring
                                     ) {

    public TransactionResponseDto(Transaction transaction) {
        this(transaction.getId(),
             new HouseholdDto(transaction.getHousehold()),
             new UserResponseData(transaction.getUser()),
             new CategoryResponseDto(transaction.getCategory()),
             transaction.getType().name(),
             transaction.getAmount().toString(),
             transaction.getDescription(),
             transaction.getTransactionDate().toString(),
             transaction.getPaymentMethod(),
             transaction.getIsRecurring());
    }
}
