package com.devent.cprogress.repository;

import com.devent.cprogress.model.CategoryType;
import com.devent.cprogress.model.Challenge;
import com.devent.cprogress.model.ChallengeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    // Buscar desafios por tipo
    List<Challenge> findByChallengeType(ChallengeType challengeType);

    // Buscar desafios por categoria
    List<Challenge> findByCategory(CategoryType categoryType);

    // Buscar desafios por tipo e categoria
    List<Challenge> findByChallengeTypeAndCategory(ChallengeType challengeType, CategoryType categoryType);

}
