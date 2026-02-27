package com.household.finance.budget.service;

import com.household.finance.budget.dto.BudgetResponseDto;
import com.household.finance.budget.dto.CreateBudgetDto;
import com.household.finance.budget.entity.Budget;
import com.household.finance.budget.filter.BudgetFilter;
import com.household.finance.budget.filter.BudgetSpecification;
import com.household.finance.budget.repository.BudgetRepository;
import com.household.finance.category.entity.Category;
import com.household.finance.category.repository.CategoryRepository;
import com.household.finance.common.service.MembershipService;
import com.household.finance.household.entity.Household;
import com.household.finance.household.repository.HouseholdRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MembershipService membershipService;

    @Transactional
    public BudgetResponseDto create(Long userId, Long householdId, CreateBudgetDto dto) {
        this.membershipService.validateMembership(userId, householdId);

        var household = findHouseholdById(householdId);

        var category = findCategoryById(dto.categoryId());

        var budget = new Budget(household, category, dto);

        this.budgetRepository.save(budget);

        return new BudgetResponseDto(budget);
    }

    public List<BudgetResponseDto> list(Long userId, Long householdId, Integer month, Integer year) {
        this.membershipService.validateMembership(userId, householdId);

        var budgetFilter = new BudgetFilter(month, year);

        Specification<Budget> specification = BudgetSpecification.withFilters(budgetFilter);

        return this.budgetRepository.findAll(specification).stream()
                .map(BudgetResponseDto::new)
                .toList();
    }

    private Household findHouseholdById(Long id) {
        return this.householdRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Household not found"));
    }

    private Category findCategoryById(Long id) {
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }
}
