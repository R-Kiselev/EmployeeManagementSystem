package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); // Добавляем поиск по username
}