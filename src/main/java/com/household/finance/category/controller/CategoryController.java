package com.household.finance.category.controller;

import com.household.finance.category.dto.CategoryResponseDto;
import com.household.finance.category.dto.CreateCategoryDto;
import com.household.finance.category.dto.UpdateCategoryDto;
import com.household.finance.category.service.CategoryService;
import com.household.finance.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/households/{householdId}/categories")
    public ResponseEntity<CategoryResponseDto> create(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestBody CreateCategoryDto dto) {
        var category = categoryService.createCategory(user.getId(), householdId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping("/households/{householdId}/categories")
    public ResponseEntity<List<CategoryResponseDto>> list(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestParam(required = false) String type) {

        var categoriesList = categoryService.listCategories(user.getId(), householdId, type);

        return ResponseEntity.ok(categoriesList);
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDto> update(
            @AuthenticationPrincipal User user,
            @PathVariable Long categoryId,
            @RequestBody UpdateCategoryDto dto) {

        var category = categoryService.updateCategory(user.getId(), categoryId, dto);

        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long categoryId) {

        categoryService.deleteCategory(user.getId(), categoryId);

        return ResponseEntity.noContent().build();
    }
}
