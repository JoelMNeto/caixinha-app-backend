package com.household.finance.transaction.entity;

import com.household.finance.category.entity.Category;
import com.household.finance.category.enumerations.TypeEnum;
import com.household.finance.household.entity.Household;
import com.household.finance.recurring.entity.RecurringTransaction;
import com.household.finance.transaction.dto.CreateTransactionDto;
import com.household.finance.transaction.dto.UpdateTransactionDto;
import com.household.finance.user.entity.User;
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
@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeEnum type;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    private String description;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "is_recurring")
    private Boolean isRecurring = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurring_transaction_id")
    private RecurringTransaction recurringTransaction;

    @Column(columnDefinition = "text")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Transaction(CreateTransactionDto dto, Household household, Category category, User user) {
        this.setHousehold(household);
        this.setUser(user);
        this.setCategory(category);
        this.setType(TypeEnum.fromString(dto.type()));
        this.setAmount(new BigDecimal(dto.amount()));
        this.setDescription(dto.description());
        this.setTransactionDate(dto.transactionDate());
        this.setPaymentMethod(dto.paymentMethod());
        this.setIsRecurring(false);
        this.setRecurringTransaction(null);
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void updateTransaction(UpdateTransactionDto dto, Category category) {
        if (category != null) {
            this.setCategory(category);
        }

        if (dto.type() != null) {
            this.setType(TypeEnum.fromString(dto.type()));
        }

        if (dto.amount() != null) {
            this.setAmount(new BigDecimal(dto.amount()));
        }

        if (dto.description() != null) {
            this.setDescription(dto.description());
        }

        if (dto.transactionDate() != null) {
            this.setTransactionDate(LocalDateTime.parse(dto.transactionDate()));
        }

        if (dto.paymentMethod() != null) {
            this.setPaymentMethod(dto.paymentMethod());
        }

        this.setUpdatedAt(LocalDateTime.now());
    }
}
