package com.devent.cprogress.controller;

import com.devent.cprogress.model.Weapon;
import com.devent.cprogress.service.WeaponService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weapons")
public class WeaponController {

    private final WeaponService weaponService;

    @Autowired
    public WeaponController(WeaponService weaponService) {
        this.weaponService = weaponService;
    }

    @PostMapping
    public ResponseEntity<Weapon> createWeapon(@RequestBody Weapon weapon) {
        Weapon createdWeapon = weaponService.createWeapon(weapon);
        return new ResponseEntity<>(weapon, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Weapon>> getAllWeapons() {
        try {
            List<Weapon> weapons = weaponService.getAllWeapons();
            return new ResponseEntity<>(weapons, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Weapon> deleteWeapon(@PathVariable Long id) {
        try {
            weaponService.deleteWeapon(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
