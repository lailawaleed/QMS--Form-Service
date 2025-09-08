package com.example.FormService.controller;

import com.example.FormService.dto.EvaluationFormDTO;
import com.example.FormService.dto.EvaluationFormRequestDto;
import com.example.FormService.service.EvaluationFormService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation-forms")
public class EvaluationFormController {

    public EvaluationFormController(EvaluationFormService evaluationFormService) {
        this.evaluationFormService = evaluationFormService;
    }

    private final EvaluationFormService evaluationFormService;


    // CREATE
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PostMapping
    public ResponseEntity<EvaluationFormDTO> create(@RequestBody EvaluationFormRequestDto requestDto) {
        EvaluationFormDTO created = evaluationFormService.createFullEvaluationForm(requestDto);
        return ResponseEntity.ok(created);
    }

    // READ - by ID
    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationFormDTO> getById(@PathVariable Long id) {
        return evaluationFormService.getEvaluationForm(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // READ - all
    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    @GetMapping
    public ResponseEntity<List<EvaluationFormDTO>> getAll() {
        return ResponseEntity.ok(evaluationFormService.getAllEvaluationForms());
    }

    // UPDATE
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationFormDTO> update(@PathVariable Long id,
                                                    @RequestBody EvaluationFormDTO dto) {
        return evaluationFormService.updateEvaluationForm(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = evaluationFormService.deleteEvaluationForm(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
