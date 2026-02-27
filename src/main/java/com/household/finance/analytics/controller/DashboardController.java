package com.household.finance.analytics.controller;

import com.household.finance.analytics.dto.CategorySummaryResponseDto;
import com.household.finance.analytics.dto.SummaryResponseDto;
import com.household.finance.analytics.service.DashboardService;
import com.household.finance.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/households/{householdId}")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/summary")
    public ResponseEntity<SummaryResponseDto> summary(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {

        var summary = service.summary(user.getId(), householdId, startDate, endDate);

        return ResponseEntity.ok(summary);
    }

    @GetMapping("/analytics/by-category")
    public ResponseEntity<List<CategorySummaryResponseDto>> byCategory(
            @AuthenticationPrincipal User user,
            @PathVariable Long householdId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {

        var analyticsByCategory = service.byCategory(user.getId(), householdId, startDate, endDate);

        return ResponseEntity.ok(analyticsByCategory);
    }
}
