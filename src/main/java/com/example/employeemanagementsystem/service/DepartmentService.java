package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dao.DepartmentDao;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.model.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    private final DepartmentDao departmentDao;

    @Autowired
    public DepartmentService(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @Transactional(readOnly = true)
    public Optional<Department> getDepartmentById(Long id) {
        return departmentDao.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Department> getAllDepartments() {
        return departmentDao.findAll();
    }

    @Transactional
    public Department createOrUpdateDepartment(Department department) {
        return departmentDao.save(department);
    }

    @Transactional
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = departmentDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));

        department.setName(departmentDetails.getName());
        department.setDescription(departmentDetails.getDescription());

        return departmentDao.save(department);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        departmentDao.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Department not found with id " + id));
        departmentDao.deleteById(id);
    }
}