package com.example.employeemanagementsystem.mapper;

import com.example.employeemanagementsystem.dao.UserDao; 
import com.example.employeemanagementsystem.dto.create.UserCreateDto; 
import com.example.employeemanagementsystem.dto.get.UserDto; 
import com.example.employeemanagementsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserMapper { 

    @Autowired
    protected UserDao userDao; 

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    public abstract User toEntity(UserCreateDto userCreateDto); 

    public abstract UserDto toDto(User user); 

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    public abstract void updateUserFromDto(UserCreateDto dto, @MappingTarget User entity); 
}