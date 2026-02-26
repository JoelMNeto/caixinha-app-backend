package com.household.finance.transaction.service;

import com.household.finance.category.entity.Category;
import com.household.finance.category.enumerations.TypeEnum;
import com.household.finance.category.repository.CategoryRepository;
import com.household.finance.common.service.MembershipService;
import com.household.finance.household.entity.Household;
import com.household.finance.household.repository.HouseholdRepository;
import com.household.finance.transaction.dto.CreateTransactionDto;
import com.household.finance.transaction.dto.TransactionResponseDto;
import com.household.finance.transaction.dto.UpdateTransactionDto;
import com.household.finance.transaction.entity.Transaction;
import com.household.finance.transaction.filter.TransactionFilter;
import com.household.finance.transaction.filter.TransactionSpecification;
import com.household.finance.transaction.repository.TransactionRepository;
import com.household.finance.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MembershipService membershipService;

    @Transactional
    public TransactionResponseDto createTransaction(User user, Long householdId, CreateTransactionDto dto) {
        this.membershipService.validateMembership(user.getId(), householdId);

        var household = findHouseholdById(householdId);

        var category = findCategoryById(dto.categoryId());

        var transaction = new Transaction(dto, household, category, user);

        this.transactionRepository.save(transaction);

        return new TransactionResponseDto(transaction);
    }

    public List<TransactionResponseDto> listTransactions(Long loggedUserId, Long householdId, LocalDateTime startDate, LocalDateTime endDate, String type, Long categoryId, Long userId) {
        this.membershipService.validateMembership(loggedUserId, householdId);

        var typeEnum = type != null ? TypeEnum.valueOf(type.toUpperCase()) : null;

        var transactionFilters = new TransactionFilter(householdId, categoryId, userId, typeEnum, startDate, endDate);

        Specification<Transaction> spec = TransactionSpecification.withFilters(transactionFilters);

        return this.transactionRepository.findAll(spec).stream()
                .map(TransactionResponseDto::new)
                .toList();
    }

    @Transactional
    public TransactionResponseDto updateTransaction(Long loggedUserId, Long transactionId, UpdateTransactionDto dto) {
        var transaction = findTransactionById(transactionId);

        this.membershipService.validateMembership(loggedUserId, transaction.getHousehold().getId());

        Category category = null;

        if (dto.categoryId() != null) {
            category = findCategoryById(dto.categoryId());
        }

        transaction.updateTransaction(dto, category);

        return new TransactionResponseDto(transaction);
    }

    @Transactional
    public void deleteTransaction(Long id, Long transactionId) {
        var transaction = findTransactionById(transactionId);

        this.membershipService.validateMembership(id, transaction.getHousehold().getId());

        this.transactionRepository.delete(transaction);
    }

    private Transaction findTransactionById(Long transactionId) {
        return this.transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
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
