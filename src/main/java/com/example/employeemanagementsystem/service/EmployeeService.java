// EmployeeService.java
package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dao.EmployeeDao;
import com.example.employeemanagementsystem.dto.create.EmployeeCreateDto;
import com.example.employeemanagementsystem.dto.get.EmployeeDto;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.mapper.EmployeeMapper;
import com.example.employeemanagementsystem.model.Employee;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao, EmployeeMapper employeeMapper) {
        this.employeeDao = employeeDao;
        this.employeeMapper = employeeMapper;
    }

    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeById(Long id) { // Возвращает Optional<Employee>
        return employeeDao.findById(id);
    }

    @Transactional(readOnly = true)
    public EmployeeDto getEmployeeDtoById(Long id) { // Добавили метод, который возвращает EmployeeDto
        return employeeDao.findById(id)
            .map(employeeMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> getAllEmployees() {
        return employeeDao.findAll().stream()
            .map(employeeMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployeesBySalaryRange(BigDecimal minSalary, BigDecimal maxSalary) {
        if (minSalary == null && maxSalary == null) {
            return employeeDao.findAll();
        } else if (minSalary == null) {
            return employeeDao.findBySalaryLessThanEqual(maxSalary);
        } else if (maxSalary == null) {
            return employeeDao.findBySalaryGreaterThanEqual(minSalary);
        } else {
            return employeeDao.findBySalaryBetween(minSalary, maxSalary);
        }
    }

    @Transactional
    public EmployeeDto createEmployee(EmployeeCreateDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        Employee savedEmployee = employeeDao.save(employee);
        return employeeMapper.toDto(savedEmployee); // Возвращаем EmployeeDto
    }

    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeCreateDto employeeDto) {
        Employee employee =
            employeeDao
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        employeeMapper.updateEmployeeFromDto(employeeDto, employee);
        Employee updatedEmployee = employeeDao.save(employee);
        return employeeMapper.toDto(updatedEmployee); // Возвращаем EmployeeDto
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeDao.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id " + id);
        }
        employeeDao.deleteById(id);
    }

    @Transactional
    public Employee updateEmployeeWithoutDto(Employee employee) {
        return employeeDao.save(employee);
    }
}