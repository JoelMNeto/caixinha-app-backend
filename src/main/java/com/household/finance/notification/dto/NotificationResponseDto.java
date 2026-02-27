package com.household.finance.notification.dto;

import com.household.finance.notification.entity.Notification;
import com.household.finance.user.dto.UserResponseData;

public record NotificationResponseDto(Long id, UserResponseData user, String title, String text, boolean isRead) {

    public NotificationResponseDto(Notification notification) {
        this(notification.getId(),
                new UserResponseData(notification.getUser()),
                notification.getTitle(), notification.getText(),
                notification.isRead());
    }
}
