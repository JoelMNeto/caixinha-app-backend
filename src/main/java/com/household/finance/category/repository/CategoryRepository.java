package com.household.finance.category.repository;

import com.household.finance.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByHouseholdId(Long householdId);
}
