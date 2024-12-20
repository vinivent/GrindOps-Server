package com.devent.cprogress.repository;

import com.devent.cprogress.model.Achievement;
import com.devent.cprogress.model.Category;
import com.devent.cprogress.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    List<Achievement> findByChallengeAndCategory(Challenge challenge, Category category);
}
