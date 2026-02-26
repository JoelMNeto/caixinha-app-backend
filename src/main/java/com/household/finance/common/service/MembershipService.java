package com.household.finance.common.service;

import com.household.finance.household.enumeration.Role;
import com.household.finance.membership.repository.HouseholdMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {

    @Autowired
    private HouseholdMemberRepository householdMemberRepository;

    public void validateOwnership(Long userId, Long householdId) {
        boolean isOwner = householdMemberRepository.existsByHouseholdIdAndUserIdAndRole(householdId, userId, Role.OWNER);

        if (!isOwner) {
            throw new RuntimeException("User is not owner of this household");
        }
    }

    public void validateMembership(Long userId, Long householdId) {
        boolean isMember = householdMemberRepository.existsByHouseholdIdAndUserId(householdId, userId);

        if (!isMember) {
            throw new RuntimeException("User is not a member of this household");
        }
    }
}
