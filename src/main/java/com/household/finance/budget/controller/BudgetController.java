package com.household.finance.budget.controller;

import com.household.finance.budget.dto.BudgetResponseDto;
import com.household.finance.budget.dto.CreateBudgetDto;
import com.household.finance.budget.service.BudgetService;
import com.household.finance.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/households/{householdId}/budgets")
    public ResponseEntity<BudgetResponseDto> create(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestBody CreateBudgetDto dto) {

        var budget = budgetService.create(user.getId(), householdId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(budget);
    }

    @GetMapping("/households/{householdId}/budgets")
    public ResponseEntity<List<BudgetResponseDto>> list(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {

        var list = budgetService.list(user.getId(), householdId, month, year);

        return ResponseEntity.ok(list);
    }
}
