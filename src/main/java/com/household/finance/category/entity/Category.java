package com.household.finance.category.entity;

import com.household.finance.category.dto.CreateCategoryDto;
import com.household.finance.category.dto.UpdateCategoryDto;
import com.household.finance.category.enumerations.TypeEnum;
import com.household.finance.household.entity.Household;
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
@Table(name="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="household_id")
    private Household household;

    private String name;

    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    private String icon;

    private String color;

    @Column(name="is_default")
    private boolean isDefault = false;

    @Column(name="created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public Category(CreateCategoryDto dto) {
        this.setName(dto.name());
        this.setType(TypeEnum.fromString(dto.type()));
        this.setIcon(dto.icon());
        this.setColor(dto.color());
    }

    public void updateHousehold(UpdateCategoryDto dto) {
        if (dto.name() != null) {
            this.setName(dto.name());
        }

        if (dto.type() != null) {
            this.setType(TypeEnum.fromString(dto.type()));
        }

        if (dto.icon() != null) {
            this.setIcon(dto.icon());
        }

        if (dto.color() != null) {
            this.setColor(dto.color());
        }
    }
}
