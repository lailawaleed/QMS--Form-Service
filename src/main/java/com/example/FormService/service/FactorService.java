package com.example.FormService.service;

import com.example.FormService.dto.FactorDto;
import com.example.FormService.mapper.FactorMapper;
import com.example.FormService.model.Category;
import com.example.FormService.model.Factor;
import com.example.FormService.repository.CategoryRepository;
import com.example.FormService.repository.FactorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class FactorService {
    private final FactorRepository factorRepository;
    private final FactorMapper factorMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public FactorService(FactorRepository factorRepository, FactorMapper factorMapper, CategoryRepository categoryRepository) {
        this.factorRepository = factorRepository;
        this.factorMapper = factorMapper;
        this.categoryRepository = categoryRepository;
    }

    public FactorDto createFactor(FactorDto factorDto) {
        Factor factor = factorMapper.toEntity(factorDto);
        Category category = categoryRepository.findById(factorDto.categoryId())
            .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        factor.setCategory(category);
        Factor saved = factorRepository.save(factor);
        return factorMapper.toDto(saved);
    }

    public List<FactorDto> getAllFactors() {
        return factorRepository.findAll().stream()
                .map(factorMapper::toDto)
                .collect(Collectors.toList());
    }

    public FactorDto getFactorById(Long id) {
        Factor factor = factorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Factor not found"));
        return factorMapper.toDto(factor);
    }

    public FactorDto updateFactor(Long id, FactorDto factorDto) {
        Factor existing = factorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Factor not found"));

        if (factorDto.categoryId() != null) {
            Category category = categoryRepository.findById(factorDto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            existing.setCategory(category);
        }
        if (factorDto.questionText() != null) existing.setQuestionText(factorDto.questionText());
        if (factorDto.weight() != null) existing.setWeight(factorDto.weight());
        if (factorDto.answerType() != null) existing.setAnswerType(factorDto.answerType());
        if (factorDto.notes() != null) existing.setNotes(factorDto.notes());
        if (factorDto.passAnswer() != null) existing.setPassAnswer(factorDto.passAnswer());

        Factor saved = factorRepository.save(existing);
        return factorMapper.toDto(saved);
    }

    public void deleteFactor(Long id) {
        factorRepository.deleteById(id);
    }
}
