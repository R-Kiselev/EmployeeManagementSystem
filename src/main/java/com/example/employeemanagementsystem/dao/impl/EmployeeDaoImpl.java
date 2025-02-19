package com.example.employeemanagementsystem.dao.impl;

import com.example.employeemanagementsystem.dao.EmployeeDao;
import com.example.employeemanagementsystem.model.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;


@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private final Map<Long, Employee> employees = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public EmployeeDaoImpl() {
        Employee emp1 =
            new Employee(idGenerator.getAndIncrement(), "John", "Doe", "john.doe@example.com",
                LocalDate.of(2023, 1, 15),
                new BigDecimal("60000"), true);
        Employee emp2 =
            new Employee(idGenerator.getAndIncrement(), "Jane", "Smith", "jane.smith@example.com",
                LocalDate.of(2022, 5, 20),
                new BigDecimal("75000"), true);
        Employee emp3 =
            new Employee(idGenerator.getAndIncrement(), "David", "Brown", "david.brown@example.com",
                LocalDate.of(2024, 3, 10),
                new BigDecimal("55000"), true);

        employees.put(emp1.getId(), emp1);
        employees.put(emp2.getId(), emp2);
        employees.put(emp3.getId(), emp3);
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(idGenerator.getAndIncrement());
        }
        employees.put(employee.getId(), employee);
        return employee;
    }
}