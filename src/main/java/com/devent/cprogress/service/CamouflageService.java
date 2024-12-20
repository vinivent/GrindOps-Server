package com.devent.cprogress.service;

import com.devent.cprogress.model.Camouflage;
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
        return camouflageRepository.save(camouflage);
    }

    public List<Camouflage> getAllCamouflages() {
        return camouflageRepository.findAll();
    }

    public Camouflage getCamouflageById(Long id) {
        return camouflageRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Camuflagem não encontrada com ID " + id));
    }

    public Camouflage updateCamouflage(Camouflage camouflage) {
        if (camouflageRepository.existsById(camouflage.getId())) {
            return camouflageRepository.save(camouflage);
        } else {
            throw new EntityNotFoundException("Camuflagem não encontrada com ID: " + camouflage.getId());
        }
    }

    public void deleteCamouflage(Long id) {
        camouflageRepository.deleteById(id);
    }

}
