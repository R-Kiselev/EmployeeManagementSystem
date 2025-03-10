package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dao.RoleDao;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.model.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleService(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional(readOnly = true)
    public Optional<Role> getRoleById(Long id) {
        return roleDao.findById(id);
    }
    @Transactional(readOnly = true)
    public Optional<Role> getRoleByName(String name) {
        return roleDao.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Transactional
    public Role createRole(Role role) {
        return roleDao.save(role);
    }

    @Transactional
    public Role updateRole(Long id, Role roleDetails) {
        Role role = roleDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));

        role.setName(roleDetails.getName());
        //Связь
        if (roleDetails.getUsers() != null && !roleDetails.getUsers().isEmpty()){
            role.setUsers(roleDetails.getUsers());
        }
        return roleDao.save(role);
    }

    @Transactional
    public void deleteRole(Long id) {
        roleDao.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Role not found with id " + id));
        roleDao.deleteById(id);
    }
}