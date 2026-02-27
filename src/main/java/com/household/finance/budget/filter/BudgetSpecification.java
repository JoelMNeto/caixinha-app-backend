package com.household.finance.budget.filter;

import com.household.finance.budget.entity.Budget;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class BudgetSpecification {

    public static Specification<Budget> withFilters(BudgetFilter filter) {
        return (root, query, cb) -> {
            if (filter.month() != null && filter.year() != null) {
                return cb.and(
                        cb.equal(root.get("budget_month"), filter.month()),
                        cb.equal(root.get("budget_year"), filter.year())
                );
            }
            return cb.conjunction();
        };
    }
}
