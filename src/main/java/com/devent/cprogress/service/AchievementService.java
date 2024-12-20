package com.devent.cprogress.service;

import com.devent.cprogress.model.Achievement;
import com.devent.cprogress.model.Category;
import com.devent.cprogress.model.Challenge;
import com.devent.cprogress.repository.AchievementRepository;
import com.devent.cprogress.repository.CategoryRepository;
import com.devent.cprogress.repository.ChallengeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final ChallengeRepository challengeRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository,
                              ChallengeRepository challengeRepository,
                              CategoryRepository categoryRepository) {
        this.achievementRepository = achievementRepository;
        this.challengeRepository = challengeRepository;
        this.categoryRepository = categoryRepository;
    }

    // CRUD

    public Achievement createAchievement(Achievement achievement) {
        return achievementRepository.save(achievement);
    }

    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    public Achievement getAchievementById(Long id) {
        return achievementRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Conquista não encontrada com ID " + id));
    }

    public Achievement updateAchievement(Achievement achievement) {
        if (achievementRepository.existsById(achievement.getId())) {
            return achievementRepository.save(achievement);
        } else {
            throw new EntityNotFoundException("Conquista não encontrada com ID " + achievement.getId());
        }
    }

    public void deleteAchievement(Long id) {
        achievementRepository.deleteById(id);
    }

    // Métodos personalizados
    public List<Achievement> getAchievementsByChallengeAndCategory(Long challengeId, Long categoryId) {
        Challenge challenge = challengeRepository.findById(challengeId).
                orElseThrow(() -> new EntityNotFoundException("Desafio não encontrado com ID " + challengeId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com ID " + categoryId));

        return achievementRepository.findByChallengeAndCategory(challenge, category);
    }
}
