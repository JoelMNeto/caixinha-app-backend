package com.household.finance.analytics.dto;

import java.math.BigDecimal;

public record CategorySummaryResponseDto(Long categoryId,
                                         String categoryName,
                                         String type,
                                         BigDecimal total) {
}
