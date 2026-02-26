package com.household.finance.membership.dto;

import com.household.finance.membership.entity.HouseholdMember;

import java.time.LocalDateTime;

public record HouseholdMemberDto(Long id,
                                 String householdName,
                                 String memberName,
                                 String role,
                                 LocalDateTime joinedAt) {

    public HouseholdMemberDto(HouseholdMember householdMember) {
        this(householdMember.getId(),
             householdMember.getHousehold().getName(),
             householdMember.getUser().getName(),
             householdMember.getRole().name(),
             householdMember.getJoinedAt());
    }
}
