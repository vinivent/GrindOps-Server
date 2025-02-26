package com.devent.cprogress.controller;

import com.devent.cprogress.model.Achievement;
import com.devent.cprogress.service.AchievementService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    @Autowired
    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @PostMapping
    public ResponseEntity<Achievement> createAchievement(@RequestBody Achievement achievement) {
        Achievement createdAchievement = achievementService.createAchievement(achievement);
        return new ResponseEntity<>(createdAchievement, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Achievement>> getAllAchievements() {
        List<Achievement> achievements = achievementService.getAllAchievements();
        return new ResponseEntity<>(achievements, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Achievement> getAchievementById(@PathVariable Long id) {
        try {
            Achievement achievement = achievementService.getAchievementById(id);
            return new ResponseEntity<>(achievement, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Achievement> updateAchievement(@PathVariable Long id, @RequestBody Achievement achievement) {
        achievement.setId(id);
        try {
            Achievement updatedAchievement = achievementService.updateAchievement(achievement);
            return new ResponseEntity<>(updatedAchievement, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Achievement> deleteAchievement(@PathVariable Long id) {
        try {
            achievementService.deleteAchievement(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search/{challengeId}/{categoryId}")
    public ResponseEntity<List<Achievement>> findByChallengeAndCategory(
            @PathVariable Long challengeId,
            @PathVariable Long categoryId) {
        try {
            List<Achievement> achievements = achievementService.getAchievementsByChallengeAndCategory(challengeId, categoryId);
            return new ResponseEntity<>(achievements, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
