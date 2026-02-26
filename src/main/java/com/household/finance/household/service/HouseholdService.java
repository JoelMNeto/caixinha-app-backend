package com.household.finance.household.service;

import com.household.finance.common.service.MembershipService;
import com.household.finance.household.dto.HouseholdDto;
import com.household.finance.household.dto.HouseholdRegistrationData;
import com.household.finance.household.entity.Household;
import com.household.finance.household.enumeration.Role;
import com.household.finance.membership.entity.HouseholdMember;
import com.household.finance.membership.repository.HouseholdMemberRepository;
import com.household.finance.user.entity.User;
import com.household.finance.household.repository.HouseholdRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private HouseholdMemberRepository householdMemberRepository;

    @Autowired
    private MembershipService membershipService;

    @Transactional
    public HouseholdDto createHousehold(@Valid HouseholdRegistrationData householdRegistrationData, User loggedUser) {
        var household = new Household(householdRegistrationData, loggedUser);

        this.householdRepository.save(household);

        var householdMember = new HouseholdMember(household, loggedUser);

        this.householdMemberRepository.save(householdMember);

        return new HouseholdDto(household);
    }

    public List<HouseholdDto> listHouseholdsByUser(Long userId) {
        var households  = householdRepository.findAllByUserId(userId);

        return households.stream().map(HouseholdDto::new).toList();
    }

    public HouseholdDto getHousehold(Long userId, Long householdId) {
        var household = findHouseholdById(householdId);

        this.membershipService.validateMembership(userId, householdId);

        return new HouseholdDto(household);
    }

    @Transactional
    public HouseholdDto updateHousehold(Long userId, Long householdId, HouseholdRegistrationData householdRegistrationData) {
        var household = findHouseholdById(householdId);

        this.membershipService.validateOwnership(userId, householdId);

        household.updateHousehold(householdRegistrationData);

        return new HouseholdDto(household);
    }

    @Transactional
    public void deleteHousehold(Long userId, Long householdId) {
        var household = findHouseholdById(householdId);

        this.membershipService.validateOwnership(userId, householdId);

        householdRepository.delete(household);
    }

    private Household findHouseholdById(Long householdId) {
        return householdRepository.findById(householdId).orElseThrow(() -> new RuntimeException("Household not found"));
    }
}
