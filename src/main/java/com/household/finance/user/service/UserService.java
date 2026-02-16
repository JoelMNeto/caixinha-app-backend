package com.household.finance.user.service;

import com.household.finance.common.service.EmailService;
import com.household.finance.user.repository.UserRepository;
import com.household.finance.user.dto.UserRegistrationData;
import com.household.finance.user.dto.UserUpdateData;
import com.household.finance.user.dto.UserUpdateEmail;
import com.household.finance.user.dto.UserUpdatePassword;
import com.household.finance.user.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUserDataByUsername(username);
    }

    public User getUserDataByUsername(String username) {
        return this.userRepository.findByEmailIgnoreCaseAndVerifiedTrueAndActiveTrue(username).orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    @Transactional
    public User register(@Valid UserRegistrationData userRegistrationData) {
        this.validatePasswordConfirmation(userRegistrationData.password(), userRegistrationData.confirmationPassword());

        var encryptedPassword = this.passwordEncoder.encode(userRegistrationData.password());

        var user = new User(userRegistrationData, encryptedPassword);

        this.emailService.sendVerificationEmail(user);

        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void verifyEmail(String token) {
        var user = this.userRepository.findByToken(token).orElseThrow();

        user.verify();
    }

    @Transactional
    public User updateUser(@Valid UserUpdateData userUpdateData, User loggedUser) {
        loggedUser.updateData(userUpdateData);

        return loggedUser;
    }

    @Transactional
    public User changeEmail(UserUpdateEmail userUpdateEmail, User loggedUser) {
        loggedUser.updateEmail(userUpdateEmail.newEmail());

        this.emailService.sendVerificationEmail(loggedUser);

        return loggedUser;
    }

    @Transactional
    public User changePassword(@Valid UserUpdatePassword userUpdatePassword, User loggedUser) {
        this.validateUpdatePassword(loggedUser, userUpdatePassword.currentPassword(),
                userUpdatePassword.newPassword(), userUpdatePassword.confirmationNewPassword());

        var encryptedNewPassword = this.passwordEncoder.encode(userUpdatePassword.newPassword());

        loggedUser.setPasswordHash(encryptedNewPassword);

        return loggedUser;
    }

    public void deleteUser(User loggedUser) {
        loggedUser.deactivate();
    }

    private void validatePasswordConfirmation(String password, String confirmationPassword) {
        if (!password.equals(confirmationPassword)) {
            throw new RuntimeException("As senhas não correspondem.");
        }
    }

    public void validateUpdatePassword(User loggedUser, String currentPassword, String newPassword, String confirmationNewPassword) {
        if (!this.passwordEncoder.matches(currentPassword, loggedUser.getPassword())) {
            throw new RuntimeException("Senha atual incorreta.");
        }

        this.validatePasswordConfirmation(newPassword, confirmationNewPassword);
    }
}
