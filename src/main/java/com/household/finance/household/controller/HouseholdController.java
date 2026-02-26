package com.household.finance.household.controller;

import com.household.finance.household.dto.HouseholdDto;
import com.household.finance.household.dto.HouseholdRegistrationData;
import com.household.finance.user.entity.User;
import com.household.finance.household.service.HouseholdService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController("/api/v1/households")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    @PostMapping
    public ResponseEntity<HouseholdDto> createHousehold(@RequestBody @Valid HouseholdRegistrationData householdRegistrationData,
                                                        @AuthenticationPrincipal User loggedUser,
                                                        UriComponentsBuilder uriBuilder) {
        HouseholdDto household = householdService.createHousehold(householdRegistrationData, loggedUser);

        var uri = uriBuilder.path("/{householdId}").buildAndExpand(household.id()).toUri();

        return ResponseEntity.created(uri).body(household);
    }

    @GetMapping
    public ResponseEntity<List<HouseholdDto>> listHouseholdsByUser(@AuthenticationPrincipal User user) {
        var householdList = householdService.listHouseholdsByUser(user.getId());

        return ResponseEntity.ok(householdList);
    }

    @GetMapping("/{householdId}")
    public ResponseEntity<HouseholdDto> getHousehold(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId) {
        var household = householdService.getHousehold(user.getId(), householdId);

        return ResponseEntity.ok(household);
    }

    @PutMapping("/{householdId}")
    public ResponseEntity<HouseholdDto> updateHousehold(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestBody @Valid HouseholdRegistrationData householdRegistrationData) {
        var household = householdService.updateHousehold(user.getId(), householdId, householdRegistrationData);

        return ResponseEntity.ok(household);
    }

    @DeleteMapping("/{householdId}")
    public ResponseEntity<Void> deleteHousehold(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId) {

        householdService.deleteHousehold(user.getId(), householdId);

        return ResponseEntity.noContent().build();
    }

}
