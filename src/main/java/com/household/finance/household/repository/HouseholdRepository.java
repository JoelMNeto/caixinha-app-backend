package com.household.finance.household.repository;

import com.household.finance.household.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
    @Query("SELECT h FROM Household h JOIN h.createdBy u WHERE u.id = :userId")
    List<Household> findAllByUserId(@Param("userId") Long userId);

    @Query("""
        SELECT DISTINCT h
        FROM Household h
        JOIN FETCH h.members hm
        JOIN FETCH hm.user
        WHERE h.id = :householdId
    """)
    Optional<Household> findByIdWithMembers(@Param("householdId") Long householdId);

    @Query("""
        SELECT DISTINCT h
        FROM Household h
        JOIN FETCH h.members hm
        JOIN FETCH hm.user
        WHERE hm.user.id = :userId
    """)
    List<Household> findAllByUserIdWithMembers(@Param("userId") Long userId);

    boolean existsByNameIgnoreCaseAndMembersUserId(String name, Long userId);
}
