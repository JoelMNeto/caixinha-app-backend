package com.household.finance.membership.entity;

import com.household.finance.household.entity.Household;
import com.household.finance.household.enumeration.Role;
import com.household.finance.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="household_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"household_id", "user_id"}))
public class HouseholdMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Role role;

    @Column(name="joined_at")
    private LocalDateTime joinedAt = LocalDateTime.now();

    public HouseholdMember(Household household, User user) {
        this.setHousehold(household);
        this.setUser(user);
        this.setRole(Role.MEMBER);
        this.setJoinedAt(LocalDateTime.now());
    }
}
