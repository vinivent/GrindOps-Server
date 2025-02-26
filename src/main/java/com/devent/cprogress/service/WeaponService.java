package com.devent.cprogress.service;

import com.devent.cprogress.model.Weapon;
import com.devent.cprogress.repository.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeaponService {

    private final WeaponRepository weaponRepository;

    @Autowired
    public WeaponService(WeaponRepository weaponRepository) {
        this.weaponRepository = weaponRepository;
    }

    // CRUD

    public Weapon createWeapon(Weapon weapon) {
        return weaponRepository.save(weapon);
    }

    public List<Weapon> getAllWeapons() {
        return weaponRepository.findAll();
    }

    public void deleteWeapon(Long id) {
        weaponRepository.deleteById(id);
    }
}
