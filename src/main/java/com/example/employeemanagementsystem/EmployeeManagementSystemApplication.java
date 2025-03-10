package com.example.employeemanagementsystem;

import com.example.employeemanagementsystem.model.Role;
import com.example.employeemanagementsystem.model.User;
import com.example.employeemanagementsystem.service.RoleService;
import com.example.employeemanagementsystem.service.UserService;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeManagementSystemApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EmployeeManagementSystemApplication.class, args);
    }

    @Bean  //  <-- Вот здесь
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    CommandLineRunner run(UserService userService, RoleService roleService) {
        return args -> {
            // Создаем роли, если их нет
            Role adminRole = roleService.getRoleByName("ADMIN").orElseGet(() -> {
                Role newAdminRole = new Role();
                newAdminRole.setName("ADMIN");
                return roleService.createRole(newAdminRole);
            });

            Role userRole = roleService.getRoleByName("USER").orElseGet(() -> {
                Role newUserRole = new Role();
                newUserRole.setName("USER");
                return roleService.createRole(newUserRole);
            });

            // Создаем админа, если его нет
            if (userService.getAllUsers().isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("adminpassword"); // Очень небезопасный пароль! Только для теста!
                admin.setRoles(Set.of(adminRole));
                userService.createUser(admin);
            }
            // Создаем обычного пользователя, если его нет
            if (userService.getAllUsers().size() < 2) {
                User user = new User();
                user.setUsername("user");
                user.setPassword("userpassword"); // Очень небезопасный пароль! Только для теста!
                user.setRoles(Set.of(userRole));
                userService.createUser(user);
            }
        };
    }
}