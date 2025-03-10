// UserService.java
package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dao.UserDao;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userDao.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) { // Добавили поиск по username
        return userDao.findByUsername(username);
    }
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.save(user);
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = userDao.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        user.setUsername(userDetails.getUsername());
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        // Обновляем связи, если они не null
        if (userDetails.getEmployee() != null) {
            user.setEmployee(userDetails.getEmployee());
        }
        if (userDetails.getRoles() != null && !userDetails.getRoles().isEmpty()) {
            user.setRoles(userDetails.getRoles());
        }

        return userDao.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userDao.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("User not found with id " + id));
        userDao.deleteById(id);
    }
}