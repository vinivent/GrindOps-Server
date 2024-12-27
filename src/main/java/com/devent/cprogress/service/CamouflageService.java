package com.devent.cprogress.service;

import com.devent.cprogress.model.Camouflage;
import com.devent.cprogress.model.CategoryType;
import com.devent.cprogress.repository.CamouflageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamouflageService {

    private final CamouflageRepository camouflageRepository;

    @Autowired
    public CamouflageService(CamouflageRepository camouflageRepository) {
        this.camouflageRepository = camouflageRepository;
    }

    // CRUD
    public Camouflage createCamouflage(Camouflage camouflage) {
        validateCamouflage(camouflage);
        return camouflageRepository.save(camouflage);
    }

    public List<Camouflage> getAllCamouflages() {
        return camouflageRepository.findAll();
    }

    public Camouflage getCamouflageById(Long id) {
        return camouflageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CCamuflagem nao encontrada com ID: " + id));
    }

    public Camouflage updateCamouflage(Camouflage camouflage) {
        if (camouflageRepository.existsById(camouflage.getId())) {
            validateCamouflage(camouflage);
            return camouflageRepository.save(camouflage);
        } else {
            throw new EntityNotFoundException("Camuflagem nao encontrada com ID: " + camouflage.getId());
        }
    }

    public String deleteCamouflage(Long id) {
        if (camouflageRepository.existsById(id)) {
            camouflageRepository.deleteById(id);
            return "Camuflagem com ID " + id + " deletada com sucesso.";
        } else {
            throw new EntityNotFoundException("Camuflagem nao encontrada com ID: " + id);
        }
    }

    // Métodos Adicionais
    public List<Camouflage> getCamouflagesByCategory(CategoryType category) {
        return camouflageRepository.findByCategory(category);
    }

    public List<Camouflage> getCamouflagesByCategoryAndAchieved(CategoryType category, boolean achieved) {
        return camouflageRepository.findByCategoryAndAchieved(category, achieved);
    }

    // Método Auxiliar para Validação
    private void validateCamouflage(Camouflage camouflage) {
        if (camouflage.getName() == null || camouflage.getName().isBlank()) {
            throw new IllegalArgumentException("Nome da camuflagem nao pode ser nulo ou vazio.");
        }
        if (camouflage.getDescription() == null || camouflage.getDescription().isBlank()) {
            throw new IllegalArgumentException("Descricao da camuflagem nao pode ser nulo ou vazio.");
        }
        if (camouflage.getCategory() == null) {
            throw new IllegalArgumentException("Categoria da camuflagem nao pode ser nula ou vazia.");
        }
    }
}
