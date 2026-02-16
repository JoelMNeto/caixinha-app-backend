package com.household.finance.user.repository;

import com.household.finance.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCaseAndVerifiedTrueAndActiveTrue(String email);

    Optional<User> findByToken(String token);
}
