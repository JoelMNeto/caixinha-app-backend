package com.household.finance.household.entity;

import com.household.finance.household.dto.HouseholdRegistrationData;
import com.household.finance.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
@Table(name="households")
public class Household {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Household(@Valid HouseholdRegistrationData householdRegistrationData, User loggedUser) {
        this.name = householdRegistrationData.name();
        this.createdBy = loggedUser;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    public void updateHousehold(HouseholdRegistrationData householdRegistrationData) {
        if (householdRegistrationData.name() != null) {
            this.name = householdRegistrationData.name();
        }

        this.setUpdatedAt(LocalDateTime.now());
    }
}
