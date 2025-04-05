package com.devent.cprogress.controller;

import com.devent.cprogress.model.CategoryType;
import com.devent.cprogress.model.Challenge;
import com.devent.cprogress.model.ChallengeType;
import com.devent.cprogress.service.ChallengeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challenges")
public class ChallengeController {

    private ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping
    public ResponseEntity<Challenge> createChallenge(@RequestBody Challenge challenge) {
        Challenge challengeCreated = challengeService.createChallenge(challenge);
        return new ResponseEntity<>(challengeCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Challenge>> getAllChallenges() {
        List<Challenge> challenges = challengeService.getAllChallenges();
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Challenge> getChallengeById(@PathVariable Long id) {
        try {
            Challenge challenge = challengeService.getChallengeById(id);
            return new ResponseEntity<>(challenge, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Challenge> updateChallenge(@PathVariable Long id, @RequestBody Challenge challenge) {
        challenge.setId(id);
        try {
            Challenge updatedChallenge = challengeService.updateChallenge(challenge);
            return new ResponseEntity<>(updatedChallenge, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Challenge> deleteChallenge(@PathVariable Long id) {
        try {
            challengeService.deleteChallenge(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/type/{challengeType}")
    public ResponseEntity<List<Challenge>> getChallengesByType(@PathVariable ChallengeType challengeType) {
        List<Challenge> challenges = challengeService.getChallengesByType(challengeType);
        return ResponseEntity.ok(challenges);
    }

    @GetMapping("/category/{categoryType}")
    public ResponseEntity<List<Challenge>> getChallengesByCategoryType(@PathVariable CategoryType categoryType) {
        List<Challenge> challenges = challengeService.getChallengesByCategoryType(categoryType);
        return ResponseEntity.ok(challenges);
    }

    @GetMapping("/type/{challengeType}/category/{categoryType}")
    public ResponseEntity<List<Challenge>> getChallengesByTypeAndCategory(
            @PathVariable ChallengeType challengeType,
            @PathVariable CategoryType categoryType
    ) {
        List<Challenge> challenges = challengeService.getChallengesByTypeAndCategory(challengeType, categoryType);
        return ResponseEntity.ok(challenges);
    }
}
