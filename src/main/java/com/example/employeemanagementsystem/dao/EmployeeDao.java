package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.model.Employee;
import java.util.List;
import java.util.Optional;


public interface EmployeeDao {
    Optional<Employee> findById(Long id);

    List<Employee> findAll();

    Employee save(Employee employee);
}