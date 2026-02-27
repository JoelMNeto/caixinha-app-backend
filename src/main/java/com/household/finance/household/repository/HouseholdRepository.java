package com.household.finance.household.repository;

import com.household.finance.household.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
    @Query("SELECT h FROM Household h JOIN h.createdBy u WHERE u.id = :userId")
    List<Household> findAllByUserId(@Param("userId") Long userId);
}
