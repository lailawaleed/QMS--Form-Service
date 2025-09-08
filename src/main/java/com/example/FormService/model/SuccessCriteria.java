package com.example.FormService.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "success_criteria",
       uniqueConstraints = @UniqueConstraint(name="uq_form_severity", columnNames = {"evaluation_form_id","severity_id"}))
public class SuccessCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_form_id", nullable = false)
    private EvaluationForm evaluationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "severity_id", nullable = false)
    private Severity severity;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal threshold;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EvaluationForm getEvaluationForm() { return evaluationForm; }
    public void setEvaluationForm(EvaluationForm evaluationForm) { this.evaluationForm = evaluationForm; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }

    public BigDecimal getThreshold() { return threshold; }
    public void setThreshold(BigDecimal threshold) { this.threshold = threshold; }


}
