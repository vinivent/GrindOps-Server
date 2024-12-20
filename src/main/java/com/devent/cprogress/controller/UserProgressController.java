package com.devent.cprogress.controller;

import com.devent.cprogress.model.Camouflage;
import com.devent.cprogress.model.UserProgress;
import com.devent.cprogress.service.UserProgressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user-progress")
public class UserProgressController {

    private final UserProgressService userProgressService;

    @Autowired
    public UserProgressController(UserProgressService userProgressService) {
        this.userProgressService = userProgressService;
    }

    @PutMapping("/achievement/{userId}/{achievementId}")
    public ResponseEntity<UserProgress> updateProgressByAchievement(@PathVariable Long userId,
                                                                  @PathVariable Long achievementId) {
        try {
            UserProgress updatedProgress = userProgressService.updateUserProgressByAchievement(userId, achievementId);
            return new ResponseEntity<>(updatedProgress, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/camouflage/{userId}/{camouflageId}")
    public ResponseEntity<UserProgress> updateProgressByCamouflage(
            @PathVariable Long userId,
            @PathVariable Long camouflageId) {
        try {
            UserProgress updatedProgress = userProgressService.updateUserProgressByCamouflage(userId, camouflageId);
            return new ResponseEntity<>(updatedProgress, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
