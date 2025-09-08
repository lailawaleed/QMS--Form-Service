package com.example.FormService.service;

import com.example.FormService.dto.ProjectDTO;
import com.example.FormService.model.Project;
import com.example.FormService.mapper.ProjectMapper;
import com.example.FormService.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @Transactional
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = projectMapper.toEntity(projectDTO);
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDTO(savedProject);
    }

    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    @Transactional(readOnly = true)
    public Optional<ProjectDTO> getProject(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toDTO);
    }

    @PreAuthorize("hasAnyRole('QA','QA_SUPERVISOR')")
    @Transactional(readOnly = true)
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @Transactional
    public Optional<ProjectDTO> updateProject(Long id, ProjectDTO projectDTO) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(projectDTO.name());
                    project.setDescription(projectDTO.description());
                    Project updatedProject = projectRepository.save(project);
                    return projectMapper.toDTO(updatedProject);
                });
    }

    @PreAuthorize("hasRole('QA_SUPERVISOR')")
    @Transactional
    public boolean deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return true;
        }
        return false;
    }
}