package com.household.finance.notification.controller;

import com.household.finance.notification.dto.NotificationResponseDto;
import com.household.finance.notification.service.NotificationService;
import com.household.finance.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> list(
            @AuthenticationPrincipal User user) {

        var notifications = notificationService.list(user.getId());

        return ResponseEntity.ok(notifications);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {

        notificationService.markAsRead(user.getId(), id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @AuthenticationPrincipal User user) {

        notificationService.markAllAsRead(user.getId());

        return ResponseEntity.noContent().build();
    }
}
