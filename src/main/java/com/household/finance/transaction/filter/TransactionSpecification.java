package com.household.finance.transaction.filter;

import com.household.finance.transaction.entity.Transaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {

    public static Specification<Transaction> withFilters(TransactionFilter filter) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(
                    root.get("household").get("id"),
                    filter.householdId()
            ));

            if (filter.categoryId() != null) {
                predicates.add(cb.equal(
                        root.get("category").get("id"),
                        filter.categoryId()
                ));
            }

            if (filter.userId() != null) {
                predicates.add(cb.equal(
                        root.get("user").get("id"),
                        filter.userId()
                ));
            }

            if (filter.type() != null) {
                predicates.add(cb.equal(
                        root.get("type"),
                        filter.type()
                ));
            }

            if (filter.startDate() != null && filter.endDate() != null) {
                predicates.add(cb.between(
                        root.get("transactionDate"),
                        filter.startDate(),
                        filter.endDate()
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
