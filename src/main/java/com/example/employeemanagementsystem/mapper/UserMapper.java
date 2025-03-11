//package com.example.employeemanagementsystem.mapper;
//
//import com.example.employeemanagementsystem.dao.UserDao;
//import com.example.employeemanagementsystem.dto.create.UserCreateDto;
//import com.example.employeemanagementsystem.dto.get.UserDto;
//import com.example.employeemanagementsystem.model.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//import org.mapstruct.NullValuePropertyMappingStrategy;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, RoleMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // добавили RoleMapper
//public interface UserMapper {
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "employee", ignore = true)
//    @Mapping(target = "roles", ignore = true) // roles устанавливаются в UserService
//    @Mapping(target = "password", ignore = true) // Пароль обрабатываем отдельно в UserService
//    User toEntity(UserCreateDto userCreateDto);
//
//
//    UserDto toDto(User user);
//
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "employee", ignore = true)
//    @Mapping(target = "roles", ignore = true)
//    @Mapping(target = "password", ignore = true) // Пароль обрабатываем отдельно в UserService
//    void updateUserFromDto(UserCreateDto dto, @MappingTarget User entity);
//
//
//}

// UserMapper.java
package com.example.employeemanagementsystem.mapper;

import com.example.employeemanagementsystem.dao.UserDao; // Предполагаем, что есть UserDao
import com.example.employeemanagementsystem.dto.create.UserCreateDto; // Если есть DTO для User
import com.example.employeemanagementsystem.dto.get.UserDto; // Если есть DTO для User
import com.example.employeemanagementsystem.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserMapper { // Или interface, если нет методов, как в PositionMapper

    @Autowired
    protected UserDao userDao; // Если нужен UserDao, делаем abstract class

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    public abstract User toEntity(UserCreateDto userCreateDto); //если есть userCreateDto

    public abstract UserDto toDto(User user); //если есть userDto

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    public abstract void updateUserFromDto(UserCreateDto dto, @MappingTarget User entity); //если есть userCreateDto

    protected User userFromId(Long userId) {
        return userId == null ? null : userDao.findById(userId).orElse(null);
    }
}