package com.devent.cprogress.service;

import com.devent.cprogress.model.Achievement;
import com.devent.cprogress.model.Camouflage;
import com.devent.cprogress.model.User;
import com.devent.cprogress.model.UserProgress;
import com.devent.cprogress.repository.UserProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProgressService {

    private final UserProgressRepository userProgressRepository;

    @Autowired
    public UserProgressService(UserProgressRepository userProgressRepository) {
        this.userProgressRepository = userProgressRepository;
    }

    public UserProgress updateUserProgressByCamouflage(Long userId, Long camouflageId) {
        UserProgress userProgress = userProgressRepository.findByUserIdAndCamouflageId(userId, camouflageId)
                .orElseGet(() -> createNewProgress(userId, camouflageId, null));

        return userProgressRepository.save(userProgress);
    }

    public UserProgress updateUserProgressByAchievement(Long userId, Long achievementId) {
        UserProgress userProgress = userProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseGet(() -> createNewProgress(userId, null, achievementId));

        return userProgressRepository.save(userProgress);
    }

    private UserProgress createNewProgress(Long userId, Long camouflageId, Long achievementId) {
        UserProgress newUserProgress = new UserProgress();

        User user = new User();
        user.setId(userId);
        newUserProgress.setUser(user);

        if (camouflageId != null) {
            Camouflage camouflage = new Camouflage();
            camouflage.setId(camouflageId);
            newUserProgress.setCamouflage(camouflage);
        }

        if (achievementId != null) {
            Achievement achievement = new Achievement();
            achievement.setId(achievementId);
            newUserProgress.setAchievement(achievement);
        }

        return newUserProgress;
    }
}
