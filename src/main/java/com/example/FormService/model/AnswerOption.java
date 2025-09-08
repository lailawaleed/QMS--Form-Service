package com.example.FormService.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "answer_option")
public class AnswerOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "factor_id", nullable = false)
    private Factor factor;

    @Column(nullable = false)
    private Integer value;

    private String label;

    @Column(name = "is_passing", nullable = false)
    private boolean isPassing;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal weight;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Factor getFactor() { return factor; }
    public void setFactor(Factor factor) { this.factor = factor; }

    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public boolean isPassing() { return isPassing; }
    public void setPassing(boolean passing) { isPassing = passing; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }
}
