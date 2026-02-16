package com.household.finance.household.controller;

import com.household.finance.household.dto.HouseholdDto;
import com.household.finance.household.dto.HouseholdRegistrationData;
import com.household.finance.user.entity.User;
import com.household.finance.household.service.HouseholdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/household")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    @PostMapping
    public ResponseEntity<HouseholdDto> createHousehold(@RequestBody @Valid HouseholdRegistrationData householdRegistrationData,
                                                        @AuthenticationPrincipal User loggedUser) {
        HouseholdDto createdHousehold = householdService.createHousehold(householdRegistrationData, loggedUser);

        return ResponseEntity.ok(createdHousehold);
    }
}
