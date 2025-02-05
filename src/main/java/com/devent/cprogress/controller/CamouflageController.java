package com.devent.cprogress.controller;

import com.devent.cprogress.model.Camouflage;
import com.devent.cprogress.model.CategoryType;
import com.devent.cprogress.service.CamouflageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/camouflages")
public class CamouflageController {

    private final CamouflageService camouflageService;

    @Autowired
    public CamouflageController(CamouflageService camouflageService) {
        this.camouflageService = camouflageService;
    }

    @PostMapping
    public ResponseEntity<Camouflage> createCamouflage(@RequestBody Camouflage camouflage) {
        Camouflage createdCamouflage = camouflageService.createCamouflage(camouflage);
        return new ResponseEntity<>(createdCamouflage, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Camouflage>> getAllCamouflages() {
        List<Camouflage> camouflages = camouflageService.getAllCamouflages();
        return new ResponseEntity<>(camouflages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Camouflage> getCamouflageById(@PathVariable Long id) {
        try {
            Camouflage camouflage = camouflageService.getCamouflageById(id);
            return new ResponseEntity<>(camouflage, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Camouflage> updateCamouflage(@PathVariable Long id, @RequestBody Camouflage camouflage) {
        camouflage.setId(id);
        try {
            Camouflage updatedCamouflage = camouflageService.updateCamouflage(camouflage);
            return new ResponseEntity<>(updatedCamouflage, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Camouflage> deleteCamouflage(@PathVariable Long id) {
        try {
            camouflageService.deleteCamouflage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Camouflage>> getCamouflagesByCategory(@PathVariable CategoryType category) {
        List<Camouflage> camouflages = camouflageService.getCamouflagesByCategory(category);
        return ResponseEntity.ok(camouflages);
    }

    @GetMapping("/category/{category}/achieved")
    public ResponseEntity<List<Camouflage>> getCamouflagesByCategory(@PathVariable CategoryType category,
                                                                     @RequestParam boolean achieved) {
        List<Camouflage> camouflages = camouflageService.getCamouflagesByCategoryAndAchieved(category, achieved);
        return ResponseEntity.ok(camouflages);
    }
}