package com.example.FormService.service;

import com.example.FormService.dto.EvaluationFormDTO;
import com.example.FormService.dto.EvaluationFormRequestDto;
import com.example.FormService.mapper.EvaluationFormMapper;
import com.example.FormService.model.*;
import com.example.FormService.repository.CategoryRepository;
import com.example.FormService.repository.EvaluationFormRepository;
import com.example.FormService.repository.ProjectRepository;
import com.example.FormService.repository.SuccessCriteriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import common.model.User;
import common.repository.UserRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationFormService {

    private final EvaluationFormRepository evaluationFormRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final SuccessCriteriaRepository successCriteriaRepository;
    private final EvaluationFormMapper evaluationFormMapper;

    // CREATE
    @Transactional
    public EvaluationFormDTO createEvaluationForm(EvaluationFormDTO dto) {
        EvaluationForm form = evaluationFormMapper.toEntity(dto);

        // Set project
        Project project = projectRepository.findById(dto.projectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        form.setProject(project);

        // Set supervisor
        User supervisor = userRepository.findById(dto.supervisorId())
                .orElseThrow(() -> new RuntimeException("Supervisor not found"));
        form.setSupervisor(supervisor);

        // Set timestamps
        Instant now = Instant.now();
        form.setCreatedAt(now);
        form.setUpdatedAt(now);

        // Optionally set categories
        if (dto.categoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(dto.categoryIds());
            if(!validateSeverityWeight(categories)) {
                throw new IllegalArgumentException("Severity weights not valid");
            }
            form.setCategories(categories);
            for(Category category : categories) {
                category.setForm(form);
            }
            //form.setCategories(categories);
            //categories.forEach(c -> c.setForm(form));
        }

        // Optionally set success criteria
        if (dto.successCriteriaIds() != null) {
            List<SuccessCriteria> criteria = successCriteriaRepository.findAllById(dto.successCriteriaIds());
            form.setSuccessCriteria(criteria);
            criteria.forEach(c -> c.setEvaluationForm(form));
        }

        EvaluationForm saved = evaluationFormRepository.save(form);
        return evaluationFormMapper.toDTO(saved);
    }

    // READ - by ID
    @Transactional(readOnly = true)
    public Optional<EvaluationFormDTO> getEvaluationForm(Long id) {
        return evaluationFormRepository.findById(id)
                .map(evaluationFormMapper::toDTO);
    }

    // READ - all
    @Transactional(readOnly = true)
    public List<EvaluationFormDTO> getAllEvaluationForms() {
        return evaluationFormRepository.findAll()
                .stream()
                .map(evaluationFormMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Transactional
    public Optional<EvaluationFormDTO> updateEvaluationForm(Long id, EvaluationFormDTO dto) {
        return evaluationFormRepository.findById(id)
                .map(form -> {
                    // MapStruct will update only non-null fields
                    evaluationFormMapper.updateEntityFromDto(dto, form);

                    form.setUpdatedAt(Instant.now());

                    EvaluationForm updated = evaluationFormRepository.save(form);
                    return evaluationFormMapper.toDTO(updated);
                });
    }



    // DELETE
    @Transactional
    public boolean deleteEvaluationForm(Long id) {
        return evaluationFormRepository.findById(id)
                .map(form -> {
                    evaluationFormRepository.delete(form);
                    return true;
                })
                .orElse(false);
    }


    private boolean validateCategoryWeight( Category category){
        BigDecimal categoryWeight = category.calculateWeight();
        return categoryWeight.compareTo(BigDecimal.valueOf(100)) == 0;
    }
    private boolean validateSeverityWeight(List<Category> categories){
        Map<Severity, List<Category>> severityGroups = createSeverityMap(categories);
        for(Map.Entry<Severity, List<Category>> entry : severityGroups.entrySet()){
            Severity severity = entry.getKey();
            List<Category> categoryList = entry.getValue();

            BigDecimal totalWeight = BigDecimal.ZERO;
            for(Category category : categoryList){
                totalWeight =  totalWeight.add(category.getWeight());
            }
            if(totalWeight.compareTo(BigDecimal.valueOf(100)) != 0){
                return false;
            }
        }
        return true;
    }
    private Map<Severity, List<Category>> createSeverityMap(List<Category> categories){
        Map<Severity, List<Category>> severityGroups = new HashMap<>();
        for(Category category : categories){
            Severity severity = category.getSeverity();
            if(!severityGroups.containsKey(severity)){
                severityGroups.put(severity, new ArrayList<>());
            }
            severityGroups.get(severity).add(category);
        }
        return severityGroups;
    }

    // -------------------------- full form-----------------

    @Transactional
    public EvaluationFormDTO createFullEvaluationForm(EvaluationFormRequestDto requestDto) {
        // Create form
        EvaluationForm form = new EvaluationForm();
        form.setNameEn(requestDto.nameEn());
        form.setNameAr(requestDto.nameAr());
        form.setCalculationMethod(requestDto.calculationMethod());
        form.setStatus(requestDto.status());

        // Project check
        Project project = projectRepository.findById(requestDto.projectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + requestDto.projectId()));
        form.setProject(project);

        // Supervisor check (existence + role)
        User supervisor = userRepository.findById(requestDto.supervisorId())
                .filter(u -> u.getRole() != null &&
                        "QA_SuperVisor".equalsIgnoreCase(u.getRole().getRoleName()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Supervisor with id " + requestDto.supervisorId() +
                                " not found or not a QA_SuperVisor"));
        form.setSupervisor(supervisor);

        form.setCreatedAt(Instant.now());
        form.setUpdatedAt(Instant.now());

        // Categories
        List<Category> categories = new ArrayList<>();
        for (var categoryReq : requestDto.categories()) {
            Category category = new Category();
            category.setTitle(categoryReq.title());
            category.setWeight(categoryReq.weight());

            Severity severity = new Severity();
            severity.setId(categoryReq.severityId());
            category.setSeverity(severity);
            category.setForm(form);

            // Factors
            List<Factor> factors = new ArrayList<>();
            for (var factorReq : categoryReq.factors()) {
                Factor factor = new Factor();
                factor.setQuestionText(factorReq.questionText());
                factor.setWeight(factorReq.weight());
                factor.setAnswerType(factorReq.answerType());
                factor.setNotes(factorReq.notes());
                factor.setPassAnswer(factorReq.passAnswer());
                factor.setCategory(category);

                // Answer options
                List<AnswerOption> options = new ArrayList<>();
                for (var optionReq : factorReq.answerOptions()) {
                    AnswerOption option = new AnswerOption();
                    option.setValue(optionReq.value());
                    option.setLabel(optionReq.label());
                    option.setPassing(optionReq.isPassing());
                    option.setWeight(optionReq.weight());
                    option.setFactor(factor);
                    options.add(option);
                }
                factor.setAnswerOptions(options);
                factors.add(factor);
            }
            category.setFactors(factors);
            categories.add(category);
        }

        form.setCategories(categories);

        // Save
        EvaluationForm savedForm = evaluationFormRepository.save(form);

        return evaluationFormMapper.toDTO(savedForm);
    }

    @Transactional
    public EvaluationFormDTO updateFullEvaluationForm(Long id, EvaluationFormRequestDto requestDto) {
        EvaluationForm form = evaluationFormRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EvaluationForm not found with id: " + id));

        // Update basic fields
        form.setNameEn(requestDto.nameEn());
        form.setNameAr(requestDto.nameAr());
        form.setCalculationMethod(requestDto.calculationMethod());
        form.setStatus(requestDto.status());
        form.setUpdatedAt(Instant.now());

        // Update project
        Project project = projectRepository.findById(requestDto.projectId())
                .orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + requestDto.projectId()));
        form.setProject(project);

        // Update supervisor
        User supervisor = userRepository.findById(requestDto.supervisorId())
                .filter(u -> u.getRole() != null &&
                        "QA_SuperVisor".equalsIgnoreCase(u.getRole().getRoleName()))
                .orElseThrow(() -> new EntityNotFoundException(
                        "Supervisor with id " + requestDto.supervisorId() +
                                " not found or not a QA_SuperVisor"));
        form.setSupervisor(supervisor);

        // clear old categories
        form.getCategories().clear();

        // Rebuild categories
        List<Category> categories = new ArrayList<>();
        for (var categoryReq : requestDto.categories()) {
            Category category = new Category();
            category.setTitle(categoryReq.title());
            category.setWeight(categoryReq.weight());

            Severity severity = new Severity();
            severity.setId(categoryReq.severityId());
            category.setSeverity(severity);
            category.setForm(form);

            // Factors
            List<Factor> factors = new ArrayList<>();
            for (var factorReq : categoryReq.factors()) {
                Factor factor = new Factor();
                factor.setQuestionText(factorReq.questionText());
                factor.setWeight(factorReq.weight());
                factor.setAnswerType(factorReq.answerType());
                factor.setNotes(factorReq.notes());
                factor.setPassAnswer(factorReq.passAnswer());
                factor.setCategory(category);

                // Answer options
                List<AnswerOption> options = new ArrayList<>();
                for (var optionReq : factorReq.answerOptions()) {
                    AnswerOption option = new AnswerOption();
                    option.setValue(optionReq.value());
                    option.setLabel(optionReq.label());
                    option.setPassing(optionReq.isPassing());
                    option.setWeight(optionReq.weight());
                    option.setFactor(factor);
                    options.add(option);
                }
                factor.setAnswerOptions(options);
                factors.add(factor);
            }
            category.setFactors(factors);
            categories.add(category);
        }
        form.setCategories(categories);

        EvaluationForm updatedForm = evaluationFormRepository.save(form);
        return evaluationFormMapper.toDTO(updatedForm);
    }

    @Transactional
    public boolean deleteFullEvaluationForm(Long id) {
        return evaluationFormRepository.findById(id)
                .map(form -> {
                    evaluationFormRepository.delete(form);
                    return true;
                })
                .orElse(false);
    }


}
