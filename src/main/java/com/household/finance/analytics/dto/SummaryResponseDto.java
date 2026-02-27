package com.household.finance.analytics.dto;

import java.math.BigDecimal;

public record SummaryResponseDto(BigDecimal totalIncome,
                                 BigDecimal totalExpense,
                                 BigDecimal balance) {
}
