package com.household.finance.recurring.controller;

import com.household.finance.recurring.dto.CreateRecurringDto;
import com.household.finance.recurring.dto.RecurringResponseDto;
import com.household.finance.recurring.service.RecurringTransactionService;
import com.household.finance.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RecurringTransactionController {

    @Autowired
    private RecurringTransactionService recurringTransactionService;

    @PostMapping("/households/{householdId}/recurring-transactions")
    public ResponseEntity<RecurringResponseDto> create(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestBody CreateRecurringDto dto) {

        var recurringTransaction = recurringTransactionService.create(user, householdId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(recurringTransaction);
    }

    @GetMapping("/households/{householdId}/recurring-transactions")
    public ResponseEntity<List<RecurringResponseDto>> list(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId) {

        var list = recurringTransactionService.list(user, householdId);

        return ResponseEntity.ok(list);
    }

    @PatchMapping("/recurring-transactions/{id}/toggle")
    public ResponseEntity<Void> toggle(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {

        recurringTransactionService.toggle(user.getId(), id);

        return ResponseEntity.noContent().build();
    }
}
