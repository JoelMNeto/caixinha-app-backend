package com.household.finance.user.dto;

import com.household.finance.user.entity.User;

public record UserResponseData(Long id,
                               String name,
                               String nickname,
                               String username) {

    public UserResponseData(User user) {
        this(user.getId(), user.getName(), user.getNickname(), user.getUsername());
    }
}
