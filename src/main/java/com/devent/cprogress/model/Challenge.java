package com.devent.cprogress.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Desafio e obrigatorio")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChallengeType challengeType;

    @NotNull(message = "Categoria e obrigatorio")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChallengeType getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(ChallengeType challengeType) {
        this.challengeType = challengeType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
