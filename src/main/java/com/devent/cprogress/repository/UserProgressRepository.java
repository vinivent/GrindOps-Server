package com.devent.cprogress.repository;

import com.devent.cprogress.model.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

    Optional<UserProgress> findByUserIdAndCamouflageId(Long userId, Long camouflageId);

    Optional<UserProgress> findByUserIdAndAchievementId(Long userId, Long achievementId);
}
