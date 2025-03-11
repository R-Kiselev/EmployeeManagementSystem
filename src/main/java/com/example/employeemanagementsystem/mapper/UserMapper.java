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
    @Mapping(target = "employee", ignore = true) // Скорее всего, employee нужно игнорировать
    public abstract User toEntity(UserCreateDto userCreateDto); //если есть userCreateDto

    public abstract UserDto toDto(User user); //если есть userDto

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    public abstract void updateUserFromDto(UserCreateDto dto, @MappingTarget User entity); //если есть userCreateDto

    // Если НУЖЕН UserDao (например, для получения User по username, а не по ID)
    // То делаем, как с Department и Position
    protected User userFromId(Long userId) { //метод для маппинга user
        return userId == null ? null : userDao.findById(userId).orElse(null);
    }
}