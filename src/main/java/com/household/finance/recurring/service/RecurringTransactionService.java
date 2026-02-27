package com.household.finance.recurring.service;

import com.household.finance.category.entity.Category;
import com.household.finance.category.repository.CategoryRepository;
import com.household.finance.common.service.MembershipService;
import com.household.finance.household.entity.Household;
import com.household.finance.household.repository.HouseholdRepository;
import com.household.finance.recurring.dto.CreateRecurringDto;
import com.household.finance.recurring.dto.RecurringResponseDto;
import com.household.finance.recurring.entity.RecurringTransaction;
import com.household.finance.recurring.repository.RecurringTransactionRepository;
import com.household.finance.user.entity.User;
import com.household.finance.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecurringTransactionService {

    @Autowired
    private RecurringTransactionRepository recurringTransactionRepository;

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public RecurringResponseDto create(User user, Long householdId, CreateRecurringDto dto) {
        this.membershipService.validateMembership(user.getId(), householdId);

        var category = findCategoryById(dto.categoryId());

        var household = findHouseholdById(householdId);

        var recurringTransaction = new RecurringTransaction(user, household, category, dto);

        this.recurringTransactionRepository.save(recurringTransaction);

        return new RecurringResponseDto(recurringTransaction);
    }

    public List<RecurringResponseDto> list(User user, Long householdId) {
        this.membershipService.validateMembership(user.getId(), householdId);

        var recurringTransactions = this.recurringTransactionRepository.findByHouseholdId(householdId);

        return recurringTransactions.stream()
                .map(RecurringResponseDto::new)
                .toList();
    }

    @Transactional
    public void toggle(Long userId, Long recurringTransactionId) {
        var recurringTransaction = findRecurringTransactionById(recurringTransactionId);

        this.membershipService.validateMembership(userId, recurringTransaction.getHousehold().getId());

        recurringTransaction.setActive(!recurringTransaction.isActive());

        this.recurringTransactionRepository.save(recurringTransaction);
    }

    private RecurringTransaction findRecurringTransactionById(Long recurringTransactionId) {
        return this.recurringTransactionRepository.findById(recurringTransactionId)
                .orElseThrow(() -> new IllegalArgumentException("Recurring transaction not found"));
    }

    private Category findCategoryById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    private Household findHouseholdById(Long id) {
        return this.householdRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Household not found"));
    }
}
