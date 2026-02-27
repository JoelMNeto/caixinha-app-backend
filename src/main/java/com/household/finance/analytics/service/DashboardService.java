package com.household.finance.analytics.service;

import com.household.finance.analytics.dto.CategorySummaryResponseDto;
import com.household.finance.analytics.dto.SummaryResponseDto;
import com.household.finance.common.service.MembershipService;
import com.household.finance.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private TransactionRepository transactionRepository;
    
    public SummaryResponseDto summary(Long userId, Long householdId, LocalDateTime startDate, LocalDateTime endDate) {
        this.membershipService.validateMembership(householdId, userId);

        return transactionRepository.getSummary(
                householdId,
                startDate,
                endDate
        );
    }

    public List<CategorySummaryResponseDto> byCategory(Long userId, Long householdId, LocalDateTime startDate, LocalDateTime endDate) {
        this.membershipService.validateMembership(householdId, userId);

        return transactionRepository.getByCategory(
                        householdId,
                        startDate,
                        endDate
                ).stream()
                .map(p -> new CategorySummaryResponseDto(
                        p.getCategoryId(),
                        p.getCategoryName(),
                        p.getType(),
                        p.getTotal()
                ))
                .toList();
    }
}
