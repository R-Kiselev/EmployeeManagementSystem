package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dao.EmployeeDao;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.model.Employee;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeDao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeDao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployeesBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        if (minSalary == null && maxSalary == null) {
            return employeeDao.findAll(); // Если оба параметра null, возвращаем всех
        } else if (minSalary == null) {
            return employeeDao.findBySalaryLessThanEqual(maxSalary); // Только maxSalary
        } else if (maxSalary == null) {
            return employeeDao.findBySalaryGreaterThanEqual(minSalary); // Только minSalary
        } else {
            return employeeDao.findBySalaryBetween(minSalary, maxSalary); // Оба параметра
        }
    }

    @Transactional
    public Employee createOrUpdateEmployee(Employee employee) {
        return employeeDao.save(employee);
    }

    @Transactional
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setHireDate(employeeDetails.getHireDate());
        employee.setSalary(employeeDetails.getSalary());
        employee.setIsActive(employeeDetails.getIsActive());

        return employeeDao.save(employee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        employeeDao.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Employee not found with id " + id));
        employeeDao.deleteById(id);
    }
}