package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dao.EmployeeDao;
import com.example.employeemanagementsystem.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeDao.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    public Employee createOrUpdateEmployee(Employee employee) {
        return employeeDao.save(employee);
    }
}