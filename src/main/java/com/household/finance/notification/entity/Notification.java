package com.household.finance.notification.entity;

import com.household.finance.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String type;

    private String title;

    @Column(columnDefinition = "text")
    private String text;

    @Column(name = "is_read")
    private boolean isRead = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Notification(User user, String title, String text) {
        this.setUser(user);
        this.setType("INFO");
        this.setTitle(title);
        this.setText(text);
        this.setRead(false);
        this.setCreatedAt(LocalDateTime.now());
    }
}
