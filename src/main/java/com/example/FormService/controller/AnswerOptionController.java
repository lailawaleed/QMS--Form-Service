package com.example.FormService.controller;

import com.example.FormService.dto.AnswerOptionDto;
import com.example.FormService.service.AnswerOptionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answer-options")
public class AnswerOptionController {

    private final AnswerOptionService service;

    public AnswerOptionController(AnswerOptionService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @GetMapping
    public ResponseEntity<List<AnswerOptionDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @GetMapping("/{id}")
    public ResponseEntity<AnswerOptionDto> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PostMapping
    public ResponseEntity<AnswerOptionDto> create(@RequestBody AnswerOptionDto dto) {
        AnswerOptionDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PutMapping("/{id}")
    public ResponseEntity<AnswerOptionDto> update(@PathVariable Long id, @RequestBody AnswerOptionDto dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
