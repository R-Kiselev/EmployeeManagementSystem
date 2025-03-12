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

    @Bean  
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}