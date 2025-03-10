package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.create.EmployeeCreateDto;
import com.example.employeemanagementsystem.dto.get.EmployeeDto;
import com.example.employeemanagementsystem.model.Employee; //  импорт Employee!
import com.example.employeemanagementsystem.service.EmployeeService;
import com.example.employeemanagementsystem.mapper.EmployeeMapper; //  импорт маппера
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper; //  маппер!

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper; // Инициализируем маппер
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        EmployeeDto employeeDto = employeeService.getEmployeeDtoById(id);
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees(
        @RequestParam(value = "min_salary", required = false) BigDecimal minSalary,
        @RequestParam(value = "max_salary", required = false) BigDecimal maxSalary) {

        List<Employee> employees = employeeService.getEmployeesBySalaryRange(minSalary, maxSalary);
        List<EmployeeDto> employeeDtos =
            employees.stream()
                .map(employeeMapper::toDto) //  маппер!
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDtos);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(
        @Valid @RequestBody EmployeeCreateDto employeeDto) {
        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(
        @PathVariable Long id, @Valid @RequestBody EmployeeCreateDto employeeDto) {
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}