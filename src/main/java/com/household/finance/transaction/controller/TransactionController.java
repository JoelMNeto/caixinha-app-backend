package com.household.finance.transaction.controller;

import com.household.finance.transaction.dto.CreateTransactionDto;
import com.household.finance.transaction.dto.TransactionResponseDto;
import com.household.finance.transaction.dto.UpdateTransactionDto;
import com.household.finance.transaction.service.TransactionService;
import com.household.finance.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/households/{householdId}/transactions")
    public ResponseEntity<TransactionResponseDto> create(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestBody CreateTransactionDto dto) {

        var transaction = transactionService.createTransaction(user, householdId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @GetMapping("/households/{householdId}/transactions")
    public ResponseEntity<List<TransactionResponseDto>> list(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long userId) {

        var transactions = transactionService.listTransactions(user.getId(), householdId, startDate, endDate, type, categoryId, userId);

        return ResponseEntity.ok(transactions);
    }

    @PutMapping("/transactions/{transactionId}")
    public ResponseEntity<TransactionResponseDto> update(
            @AuthenticationPrincipal User user,
            @PathVariable Long transactionId,
            @RequestBody UpdateTransactionDto dto) {

        var transaction = transactionService.updateTransaction(user.getId(), transactionId, dto);

        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/transactions/{transactionId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long transactionId) {

        transactionService.deleteTransaction(user.getId(), transactionId);

        return ResponseEntity.noContent().build();
    }
}
