package com.household.finance.user.dto;

public record ResetCodeResponseData(String resetCode) {

    public ResetCodeResponseData(String resetCode) {
        this.resetCode = resetCode;
    }
}
