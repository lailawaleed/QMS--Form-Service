package com.example.FormService.repository;

import com.example.quality_management_service.form.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
