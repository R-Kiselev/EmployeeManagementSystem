// EmployeeMapper.java
package com.example.employeemanagementsystem.mapper;

import com.example.employeemanagementsystem.dto.create.EmployeeCreateDto;
import com.example.employeemanagementsystem.dto.get.EmployeeDto;
import com.example.employeemanagementsystem.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {DepartmentMapper.class, PositionMapper.class, UserMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    // EmployeeCreateDto -> Employee (для создания и обновления)
    @Mapping(target = "id", ignore = true) // ID игнорируем, он генерируется БД
    @Mapping(source = "departmentId", target = "department.id") // Явный маппинг departmentId
    @Mapping(source = "positionId", target = "position.id") // Явный маппинг positionId
    @Mapping(source = "userId", target = "user.id") // Явный маппинг userId
    Employee toEntity(EmployeeCreateDto dto);

    // Employee -> EmployeeDto (для GET запросов)
    EmployeeDto toDto(Employee entity);

    // Для обновления существующего Employee
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "departmentId", target = "department.id")
    @Mapping(source = "positionId", target = "position.id")
    @Mapping(source = "userId", target = "user.id")
    void updateEmployeeFromDto(EmployeeCreateDto dto, @MappingTarget Employee entity);
}