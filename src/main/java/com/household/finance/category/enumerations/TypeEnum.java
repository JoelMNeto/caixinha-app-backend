package com.household.finance.category.enumerations;

public enum TypeEnum {
    INCOME,
    EXPENSE;

    public static TypeEnum fromString(String type) {
        try {
            return TypeEnum.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid category type: " + type);
        }
    }
}
