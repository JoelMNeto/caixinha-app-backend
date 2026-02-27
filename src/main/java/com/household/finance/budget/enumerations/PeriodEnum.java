package com.household.finance.budget.enumerations;

public enum PeriodEnum {
    DAILY,
    WEEKLY,
    MONTHLY,
    QUARTERLY,
    YEARLY;

    public static PeriodEnum fromString(String period) {
        try {
            return PeriodEnum.valueOf(period.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
}
