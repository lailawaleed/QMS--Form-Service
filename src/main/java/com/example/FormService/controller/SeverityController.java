package com.example.FormService.controller;

import com.example.FormService.dto.SeverityDto;
import com.example.FormService.service.SeverityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/severities")
public class SeverityController {

    private final SeverityService service;

    public SeverityController(SeverityService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    @GetMapping
    public List<SeverityDto> getAll() {
        return service.getAllSeverities();
    }
}
