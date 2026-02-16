package com.household.finance.user.entity;

import com.household.finance.user.dto.UserRegistrationData;
import com.household.finance.user.dto.UserUpdateData;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.SecureRandom;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name="nickname")
    private String nickname;

    private String email;

    @Column(name="password_hash")
    private String passwordHash;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    private Boolean verified = false;

    private Boolean active = false;

    @Column(name="confirmation_code")
    private Integer confirmationCode;

    @Column(name="confirmation_code_expires")
    private LocalDateTime confirmationCodeExpires;

    public User(UserRegistrationData userRegistrationData, String passwordEncoder) {
        this.setName(userRegistrationData.name());
        this.setNickname(userRegistrationData.nickname());
        this.setEmail(userRegistrationData.email());
        this.setPasswordHash(passwordEncoder);
        this.setActive(false);
        this.setVerified(false);
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
        this.setConfirmationCode(100_000 + SECURE_RANDOM.nextInt(900_000));
        this.setConfirmationCodeExpires(LocalDateTime.now().plusMinutes(30));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public void verify() throws RuntimeException {
        if (this.getConfirmationCodeExpires().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Expired Token!");
        }

        this.setVerified(true);
        this.setActive(true);
        this.setConfirmationCode(null);
        this.setConfirmationCodeExpires(null);
    }

    public void updateData(@Valid UserUpdateData userUpdateData) {
        if (userUpdateData.name() != null) {
            this.setName(userUpdateData.name());
        }

        if (userUpdateData.nickname() != null) {
            this.setNickname(userUpdateData.nickname());
        }

        this.setUpdatedAt(LocalDateTime.now());
    }

    public void updateEmail(String newEmail) {
        this.setEmail(newEmail);
        this.setVerified(false);
        this.setActive(false);
        this.setConfirmationCode(100_000 + User.SECURE_RANDOM.nextInt(900_000));
        this.setConfirmationCodeExpires(LocalDateTime.now().plusMinutes(30));
    }

    public void deactivate() {
        this.setActive(false);
    }
}
