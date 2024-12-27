package com.devent.cprogress.service;

import com.devent.cprogress.model.CategoryType;
import com.devent.cprogress.model.Challenge;
import com.devent.cprogress.model.ChallengeType;
import com.devent.cprogress.repository.ChallengeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Autowired
    public ChallengeService(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    // CRUD

    public Challenge createChallenge(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }

    public Challenge getChallengeById(Long id) {
        return challengeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Desafio não encontrado com ID " + id));
    }

    public Challenge updateChallenge(Challenge challenge) {
        if (challengeRepository.existsById(challenge.getId())) {
            return challengeRepository.save(challenge);
        } else {
            throw new EntityNotFoundException("Desafio não encontrado com ID " + challenge.getId());
        }
    }

    public void deleteChallenge(Long id) {
        challengeRepository.deleteById(id);
    }

    // Métodos adicionais

    // Buscar desafios por tipo de desafio
    public List<Challenge> getChallengesByType(ChallengeType challengeType) {
        return challengeRepository.findByChallengeType(challengeType);
    }

    // Buscar desafios por tipo de categoria
    public List<Challenge> getChallengesByCategoryType(CategoryType categoryType) {
        return challengeRepository.findByCategory(categoryType);
    }

    // Buscar desafios por tipo de desafio e tipo de categoria
    public List<Challenge> getChallengesByTypeAndCategory(ChallengeType challengeType, CategoryType categoryType) {
        return challengeRepository.findByChallengeTypeAndCategory(challengeType, categoryType);
    }
}