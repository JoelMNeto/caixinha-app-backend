package com.household.finance.membership.repository;

import com.household.finance.household.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdMemberRepository extends JpaRepository<Household, Long> {
}
