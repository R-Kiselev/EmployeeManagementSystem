// UserService.java
package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dao.EmployeeDao;
import com.example.employeemanagementsystem.dao.RoleDao;
import com.example.employeemanagementsystem.dao.UserDao;
import com.example.employeemanagementsystem.dto.create.UserCreateDto;
import com.example.employeemanagementsystem.dto.get.UserDto;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.mapper.UserMapper;
import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.model.Role;
import com.example.employeemanagementsystem.model.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserMapper userMapper;
    private final EmployeeDao employeeDao; // Добавляем EmployeeDao
    private final RoleDao roleDao; // Добавляем RoleDao
    private final PasswordEncoder passwordEncoder; // Добавляем PasswordEncoder
    private final RoleService roleService;


    @Autowired
    public UserService(UserDao userDao, UserMapper userMapper, EmployeeDao employeeDao,
                       RoleDao roleDao, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.employeeDao = employeeDao; // Внедряем EmployeeDao
        this.roleDao = roleDao;  // Внедряем RoleDao
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long id) {
        return userDao.findById(id)
            .map(userMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        return userDao.findByUsername(username)
            .map(userMapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userDao.findAll().stream()
            .map(userMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public UserDto createUser(UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);

        // Устанавливаем Employee
        Employee employee = employeeDao.findById(userCreateDto.getEmployeeId())
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + userCreateDto.getEmployeeId()));
        user.setEmployee(employee);

        // Устанавливаем и хэшируем пароль
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        // Устанавливаем роли, если они предоставлены.  Если нет - роль USER по умолчанию.
        Set<Role> roles = new HashSet<>();
        if (userCreateDto.getRoleIds() != null && !userCreateDto.getRoleIds().isEmpty()) {
            userCreateDto.getRoleIds().forEach(roleId -> {
                Role role = roleDao.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
                roles.add(role);
            });
        } else {
            roles.add(roleService.findRoleByName("USER"));
        }
        user.setRoles(roles);

        User savedUser = userDao.save(user);
        return userMapper.toDto(savedUser);
    }



    @Transactional
    public UserDto updateUser(Long id, UserCreateDto userCreateDto) {
        User user = userDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        userMapper.updateUserFromDto(userCreateDto, user);

        // Обновляем Employee, если указан
        if(userCreateDto.getEmployeeId() != null) {
            Employee employee =
                employeeDao
                    .findById(userCreateDto.getEmployeeId())
                    .orElseThrow(
                        () ->
                            new ResourceNotFoundException(
                                "Employee not found with id " + userCreateDto.getEmployeeId()));
            user.setEmployee(employee);
        }


        // Обновляем пароль, если предоставлен новый
        if (userCreateDto.getPassword() != null && !userCreateDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        }

        // Обновляем роли
        if (userCreateDto.getRoleIds() != null) {
            Set<Role> newRoles = new HashSet<>();
            for (Long roleId : userCreateDto.getRoleIds()) {
                Role role = roleDao.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));
                newRoles.add(role);
            }
            user.setRoles(newRoles);
        }

        User updatedUser = userDao.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userDao.deleteById(id);
    }
}