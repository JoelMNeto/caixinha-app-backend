package com.household.finance.user.dto;

import com.household.finance.user.entity.User;

import java.time.LocalDateTime;

public record UserResponseData(Long id,
                               String name,
                               String nickname,
                               String username,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt) {

    public UserResponseData(User user) {
        this(user.getId(), user.getName(), user.getNickname(), user.getUsername(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
