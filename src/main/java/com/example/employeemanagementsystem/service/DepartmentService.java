// DepartmentService.java
package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dao.DepartmentDao;
import com.example.employeemanagementsystem.dto.create.DepartmentCreateDto;
import com.example.employeemanagementsystem.dto.get.DepartmentDto;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.model.Department;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    private final DepartmentDao departmentDao;
    private final ModelMapper modelMapper;

    @Autowired
    public DepartmentService(DepartmentDao departmentDao, ModelMapper modelMapper) {
        this.departmentDao = departmentDao;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public DepartmentDto getDepartmentById(Long id) {
        return departmentDao
            .findById(id)
            .map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> getAllDepartments() {
        return departmentDao.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public DepartmentDto createDepartment(
        DepartmentCreateDto departmentCreateDto) { // Используем DepartmentCreateDto
        Department department = modelMapper.map(departmentCreateDto, Department.class);
        Department savedDepartment = departmentDao.save(department);
        return convertToDto(savedDepartment);
    }

    @Transactional
    public DepartmentDto updateDepartment(
        Long id, DepartmentCreateDto departmentCreateDto) { // Используем DepartmentCreateDto
        Department department =
            departmentDao
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));

        modelMapper.map(departmentCreateDto, department);
        Department updatedDepartment = departmentDao.save(department);
        return convertToDto(updatedDepartment);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        departmentDao
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        departmentDao.deleteById(id);
    }

    private DepartmentDto convertToDto(Department department) {
        return modelMapper.map(department, DepartmentDto.class);
    }
}