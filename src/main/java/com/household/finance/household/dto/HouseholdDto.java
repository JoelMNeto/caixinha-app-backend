package com.household.finance.household.dto;

import com.household.finance.household.entity.Household;
import com.household.finance.user.dto.UserResponseData;
import com.household.finance.membership.entity.HouseholdMember;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record HouseholdDto(Long id,
                           String name,
                           Long createdBy,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           List<UserResponseData> members) {

    public HouseholdDto(Household household) {
        this(household.getId(),
                household.getName(),
                household.getCreatedBy().getId(),
                household.getCreatedAt(),
                household.getUpdatedAt(),
                Optional.ofNullable(household.getMembers())
                        .orElse(new ArrayList<HouseholdMember>()).stream()
                        .map(membership -> new UserResponseData(membership.getUser())).toList());
    }
}
