package com.household.finance.category.service;

import com.household.finance.category.dto.CategoryResponseDto;
import com.household.finance.category.dto.CreateCategoryDto;
import com.household.finance.category.dto.UpdateCategoryDto;
import com.household.finance.category.entity.Category;
import com.household.finance.category.repository.CategoryRepository;
import com.household.finance.common.service.MembershipService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private MembershipService membershipService;

    @Transactional
    public CategoryResponseDto createCategory(Long userId, Long householdId, CreateCategoryDto dto) {
        this.membershipService.validateMembership(userId, householdId);

        var category = new Category(dto);

        return new CategoryResponseDto(category);
    }

    public List<CategoryResponseDto> listCategories(Long userId, Long householdId, String type) {
        this.membershipService.validateMembership(userId, householdId);

        var categories = repository.findByHouseholdId(householdId).stream()
                .filter(c -> type == null || c.getType().toString().equalsIgnoreCase(type))
                .toList();

        return categories.stream().map(CategoryResponseDto::new).toList();
    }

    @Transactional
    public CategoryResponseDto updateCategory(Long userId, Long categoryId, UpdateCategoryDto dto) {
        var category = getCategoryById(categoryId);

        this.membershipService.validateMembership(userId, category.getHousehold().getId());

        category.updateHousehold(dto);

        repository.save(category);

        return new CategoryResponseDto(category);
    }

    @Transactional
    public void deleteCategory(Long userId, Long categoryId) {
        var category = getCategoryById(categoryId);

        this.membershipService.validateMembership(userId, category.getHousehold().getId());

        repository.delete(category);
    }

    private Category getCategoryById(Long categoryId) {
        return repository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
