package com.household.finance.notification.service;

import com.household.finance.notification.dto.NotificationResponseDto;
import com.household.finance.notification.entity.Notification;
import com.household.finance.notification.repository.NotificationRepository;
import com.household.finance.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    @Transactional
    public void createNotification(User user, String title, String text) {
        var notification = new Notification(user, title, text);

        repository.save(notification);
    }

    public List<NotificationResponseDto> list(Long userId) {
        var notifications = repository.findByUserId(userId);

        return notifications.stream()
                .map(NotificationResponseDto::new)
                .toList();
    }


    @Transactional
    public void markAsRead(Long userId, Long notificationId) {
        var notification = findNotificationById(notificationId);

        notification.setRead(true);

        repository.save(notification);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        var notifications = repository.findByUserId(userId);

        notifications.forEach(notification -> notification.setRead(true));

        repository.saveAll(notifications);
    }

    private Notification findNotificationById(Long notificationId) {
        return repository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }
}
