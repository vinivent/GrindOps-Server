package com.devent.cprogress.repository;

import com.devent.cprogress.model.Category;
import com.devent.cprogress.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
