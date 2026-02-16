package com.household.finance.household.service;

import com.household.finance.household.dto.HouseholdDto;
import com.household.finance.household.dto.HouseholdRegistrationData;
import com.household.finance.user.entity.User;
import com.household.finance.household.repository.HouseholdRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;

    public HouseholdDto createHousehold(@Valid HouseholdRegistrationData householdRegistrationData, User loggedUser) {

    }
}
