package com.household.finance.recurring.enumerations;

public enum FrequencyEnum {
    MONTHLY,
    QUARTERLY,
    SEMIANNUALLY,
    ANNUALLY;

    public static FrequencyEnum fromString(String frequency) {
        try {
            return FrequencyEnum.valueOf(frequency.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid frequency: " + frequency);
        }
    }
}
