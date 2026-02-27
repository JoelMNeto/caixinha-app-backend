package com.household.finance.analytics.projection;

import java.math.BigDecimal;

public interface CategorySummaryProjection {
    Long getCategoryId();
    String getCategoryName();
    String getType();
    BigDecimal getTotal();
}
