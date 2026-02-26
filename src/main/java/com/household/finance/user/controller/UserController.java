package com.household.finance.user.controller;

import com.household.finance.user.dto.UserResponseData;
import com.household.finance.user.service.UserService;
import com.household.finance.user.dto.UserRegistrationData;
import com.household.finance.user.dto.UserUpdateData;
import com.household.finance.user.dto.UserUpdateEmail;
import com.household.finance.user.dto.UserUpdatePassword;
import com.household.finance.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseData> register(@RequestBody @Valid UserRegistrationData userRegistrationData, UriComponentsBuilder uriBuilder) {
        var user = this.userService.register(userRegistrationData);

        var uri = uriBuilder.path("/{username}").buildAndExpand(user.username()).toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping("/verify-account")
    public ResponseEntity<Void> verifyEmail(@RequestParam String token) {
        this.userService.verifyEmail(token);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseData> getUserByUsername(@RequestParam String username) {
        var user = this.userService.getUserDataByUsername(username);

        return ResponseEntity.ok(new UserResponseData(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponseData> updateUser(@RequestBody @Valid UserUpdateData userUpdateData, @AuthenticationPrincipal User loggedUser) {
        var user = this.userService.updateUser(userUpdateData, loggedUser);

        return ResponseEntity.ok(user);
    }

    @PutMapping("/me/change-email")
    public ResponseEntity<UserResponseData> changeEmail(@RequestBody @Valid UserUpdateEmail userUpdateEmail, @AuthenticationPrincipal User loggedUser) {
        var user = this.userService.changeEmail(userUpdateEmail, loggedUser);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/me/change-password")
    public ResponseEntity<UserResponseData> changePassword(@RequestBody @Valid UserUpdatePassword userUpdatePassword, @AuthenticationPrincipal User loggedUser) {
        var user = this.userService.changePassword(userUpdatePassword, loggedUser);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User loggedUser) {
        this.userService.deleteUser(loggedUser);

        return ResponseEntity.noContent().build();
    }
}
