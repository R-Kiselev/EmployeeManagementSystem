package com.example.employeemanagementsystem.model;

import java.math.BigDecimal;

public class Position {

    private Long id;
    private String name;
    private String description;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;

    public Position() {}

    public Position(final Long id, final String name, final String description, final BigDecimal minSalary, final BigDecimal maxSalary) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(final BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(final BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }
}