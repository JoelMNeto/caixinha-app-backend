package com.household.finance.household.repository;

import com.household.finance.household.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
}
