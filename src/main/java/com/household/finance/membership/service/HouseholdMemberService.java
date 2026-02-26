package com.household.finance.membership.service;

import com.household.finance.common.service.MembershipService;
import com.household.finance.household.repository.HouseholdRepository;
import com.household.finance.membership.dto.AddMemberDto;
import com.household.finance.membership.dto.HouseholdMemberDto;
import com.household.finance.membership.entity.HouseholdMember;
import com.household.finance.membership.repository.HouseholdMemberRepository;
import com.household.finance.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdMemberService {

    @Autowired
    private HouseholdMemberRepository memberRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MembershipService membershipService;

    public List<HouseholdMemberDto> listMembers(Long userId, Long householdId) {
        this.membershipService.validateMembership(userId, householdId);

        return memberRepository.findByHouseholdIdAndUserId(householdId, userId).stream()
                .map(HouseholdMemberDto::new)
                .toList();
    }

    @Transactional
    public HouseholdMemberDto addMember(Long userId, Long householdId, AddMemberDto dto) {
        this.membershipService.validateMembership(userId, householdId);

        var household = householdRepository.findById(householdId).orElseThrow(() -> new RuntimeException("Household not found"));

        var user = userRepository.findById(dto.userId()).orElseThrow(() -> new RuntimeException("User not found"));

        var householdMember = new HouseholdMember(household, user);

        return new HouseholdMemberDto(memberRepository.save(householdMember));
    }

    @Transactional
    public void removeMember(Long userId, Long householdId, Long memberId) {
        this.membershipService.validateOwnership(userId, householdId);

        var member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        memberRepository.delete(member);
    }
}
