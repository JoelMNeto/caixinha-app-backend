package com.household.finance.membership.repository;

import com.household.finance.household.enumeration.Role;
import com.household.finance.membership.entity.HouseholdMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseholdMemberRepository extends JpaRepository<HouseholdMember, Long> {
    boolean existsByHouseholdIdAndUserId(Long householdId, Long userId);

    boolean existsByHouseholdIdAndUserIdAndRole(Long householdId, Long userId, Role role);

    List<HouseholdMember> findByHouseholdIdAndUserId(Long householdId, Long userId);
}
