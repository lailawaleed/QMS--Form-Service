package com.example.FormService.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "severity",
        uniqueConstraints = @UniqueConstraint(name = "uq_severity_name", columnNames = "name")
)
public class Severity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    private String description;

    @Column(precision = 6, scale = 2)
    private BigDecimal threshold;

    @OneToMany(mappedBy = "severity")
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "severity")
    private List<SuccessCriteria> successCriteriaList = new ArrayList<>();


    public Severity() {
    }
    public Severity(String name, String description, BigDecimal threshold) {
        this.name = name;
        this.description = description;
        this.threshold = threshold;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getThreshold() { return threshold; }
    public void setThreshold(BigDecimal threshold) { this.threshold = threshold; }

    public List<Category> getCategories() { return categories; }
    public void setCategories(List<Category> categories) { this.categories = categories; }

    public List<SuccessCriteria> getSuccessCriteriaList() { return successCriteriaList; }
    public void setSuccessCriteriaList(List<SuccessCriteria> successCriteriaList) { this.successCriteriaList = successCriteriaList; }
}
