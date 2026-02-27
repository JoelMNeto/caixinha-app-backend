package com.household.finance.transaction.repository;

import com.household.finance.analytics.dto.SummaryResponseDto;
import com.household.finance.analytics.projection.CategorySummaryProjection;
import com.household.finance.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    @Query("""
       SELECT new com.household.finance.analytics.dto.SummaryResponseDto(
           COALESCE(SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END), 0),
           COALESCE(SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END), 0),
           COALESCE(SUM(CASE WHEN t.type = 'INCOME' THEN t.amount ELSE 0 END), 0) -
           COALESCE(SUM(CASE WHEN t.type = 'EXPENSE' THEN t.amount ELSE 0 END), 0)
       )
       FROM Transaction t
       WHERE t.household.id = :householdId
       AND t.transactionDate BETWEEN :start AND :end
       """)
    SummaryResponseDto getSummary(
            Long householdId,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("""
       SELECT 
           c.id AS categoryId,
           c.name AS categoryName,
           c.type AS type,
           SUM(t.amount) AS total
       FROM Transaction t
       JOIN t.category c
       WHERE t.household.id = :householdId
       AND t.transactionDate BETWEEN :start AND :end
       GROUP BY c.id, c.name, c.type
       """)
    List<CategorySummaryProjection> getByCategory(
            Long householdId,
            LocalDateTime start,
            LocalDateTime end
    );
}
