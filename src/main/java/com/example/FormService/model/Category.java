package com.example.FormService.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category" , uniqueConstraints = { @UniqueConstraint(columnNames = "title") })
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_form_id", nullable = false)
    private EvaluationForm form;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "severity_id", nullable = false)
    private Severity severity;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Factor> factors = new ArrayList<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EvaluationForm getForm() { return form; }
    public void setForm(EvaluationForm form) { this.form = form; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public Severity getSeverity() { return severity; }
    public void setSeverity(Severity severity) { this.severity = severity; }

    public List<Factor> getFactors() { return factors; }
    public void setFactors(List<Factor> factors) { this.factors = factors; }

    public BigDecimal calculateWeight(){
        BigDecimal total = BigDecimal.ZERO;
        for(Factor factor : factors){
            if(factor.getWeight() != null) {
                total = total.add(factor.getWeight());
            }
        }
        return total;
    }
}
