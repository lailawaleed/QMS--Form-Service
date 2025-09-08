package com.example.FormService.service;

import com.example.FormService.dto.CategoryDto;
import com.example.FormService.mapper.CategoryMapper;
import com.example.FormService.model.Category;
import com.example.FormService.model.EvaluationForm;
import com.example.FormService.model.Severity;
import com.example.FormService.repository.CategoryRepository;
import com.example.FormService.repository.EvaluationFormRepository;
import com.example.FormService.repository.SeverityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final EvaluationFormRepository evaluationFormRepository;
    private final SeverityRepository severityRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper, EvaluationFormRepository evaluationFormRepository, SeverityRepository severityRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.evaluationFormRepository = evaluationFormRepository;
        this.severityRepository = severityRepository;
    }
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }
    public CategoryDto findById(Long id) {
        return categoryRepository.findById(id).map(categoryMapper::toDto).orElse(null);
    }
    /*
    public CategoryDto findByName(String name) {
    }*/
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        EvaluationForm form = evaluationFormRepository.findById(categoryDto.formId())
            .orElseThrow(() -> new EntityNotFoundException("Form not found"));
        category.setForm(form);
        Severity severity = severityRepository.findById(categoryDto.severityId())
            .orElseThrow(() -> new EntityNotFoundException("Severity not found"));
        category.setSeverity(severity);
        return categoryMapper.toDto(categoryRepository.save(category));
    }
    public CategoryDto updateCategory(Long categoryId, CategoryDto updatedData) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        if (updatedData.title() != null) {
            category.setTitle(updatedData.title());
        }

        if (updatedData.weight() != null) {
            category.setWeight(updatedData.weight());
        }


        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }
}
