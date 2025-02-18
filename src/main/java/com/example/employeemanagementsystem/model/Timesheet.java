package com.example.employeemanagementsystem.model;

import java.time.LocalDate;

public class Timesheet {

    private Long id;
    private LocalDate date;
    private Integer workedHours;

    public Timesheet() {}

    public Timesheet(final Long id, final LocalDate date, final Integer workedHours) {
        this.id = id;
        this.date = date;
        this.workedHours = workedHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public Integer getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(final Integer workedHours) {
        this.workedHours = workedHours;
    }
}