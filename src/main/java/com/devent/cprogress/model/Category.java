package com.devent.cprogress.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Categoria e obrigatorio")
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }
}

