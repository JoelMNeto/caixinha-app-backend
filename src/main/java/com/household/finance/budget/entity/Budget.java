package com.household.finance.budget.entity;

import com.household.finance.budget.dto.CreateBudgetDto;
import com.household.finance.budget.enumerations.PeriodEnum;
import com.household.finance.category.entity.Category;
import com.household.finance.household.entity.Household;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PeriodEnum period;

    @Column(name = "budget_month")
    private Integer budgetMonth;

    @Column(name = "budget_year")
    private Integer budgetYear;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Budget(Household household, Category category, CreateBudgetDto dto) {
        this.setHousehold(household);
        this.setAmount(new BigDecimal(dto.amount()));
        this.setPeriod(PeriodEnum.valueOf(dto.period()));
        this.setBudgetMonth(dto.budgetMonth());
        this.setBudgetYear(dto.budgetYear());
        this.setCategory(category);
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }
}
