package com.household.finance.membership.controller;

import com.household.finance.membership.dto.AddMemberDto;
import com.household.finance.membership.dto.HouseholdMemberDto;
import com.household.finance.membership.service.HouseholdMemberService;
import com.household.finance.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/households/{householdId}/members")
public class HouseholdMemberController {

    @Autowired
    private HouseholdMemberService householdMemberService;

    @GetMapping
    public ResponseEntity<List<HouseholdMemberDto>> listMembers(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId) {
        var listMembers = this.householdMemberService.listMembers(user.getId(), householdId);

        return ResponseEntity.ok(listMembers);
    }

    @PostMapping
    public ResponseEntity<HouseholdMemberDto> addMember(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestBody AddMemberDto dto) {
        var newMember = this.householdMemberService.addMember(user.getId(), householdId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> remove(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @PathVariable Long memberId) {

        this.householdMemberService.removeMember(user.getId(), householdId, memberId);

        return ResponseEntity.noContent().build();
    }
}
