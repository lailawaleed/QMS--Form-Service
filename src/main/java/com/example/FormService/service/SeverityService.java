package com.example.FormService.service;

import com.example.FormService.dto.SeverityDto;
import com.example.FormService.mapper.SeverityMapper;
import com.example.FormService.repository.SeverityRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeverityService {

    private final SeverityRepository repository;
    private final SeverityMapper mapper;

    public SeverityService(SeverityRepository repository, SeverityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    public List<SeverityDto> getAllSeverities() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
