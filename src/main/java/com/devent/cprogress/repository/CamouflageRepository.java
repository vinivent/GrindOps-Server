package com.devent.cprogress.repository;

import com.devent.cprogress.model.Camouflage;
import com.devent.cprogress.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CamouflageRepository extends JpaRepository<Camouflage, Long> {
    List<Camouflage> findByCategory(CategoryType category);
    List<Camouflage> findByCategoryAndAchieved(CategoryType category, boolean achieved);
}
