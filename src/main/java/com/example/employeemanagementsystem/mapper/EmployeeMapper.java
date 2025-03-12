package com.example.employeemanagementsystem.mapper;

import com.example.employeemanagementsystem.dao.DepartmentDao;
import com.example.employeemanagementsystem.dao.PositionDao;
import com.example.employeemanagementsystem.dto.create.EmployeeCreateDto;
import com.example.employeemanagementsystem.dto.get.EmployeeDto;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.model.Department;
import com.example.employeemanagementsystem.model.Employee;
import com.example.employeemanagementsystem.model.Position;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {DepartmentMapper.class,
    PositionMapper.class, UserMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class EmployeeMapper {

    @Autowired
    protected DepartmentDao departmentDao;

    @Autowired
    protected PositionDao positionDao;

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "positionId", target = "position")
    public abstract Employee toEntity(EmployeeCreateDto dto);

    public abstract EmployeeDto toDto(Employee entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "positionId", target = "position")
    public abstract void updateEmployeeFromDto(EmployeeCreateDto dto,
                                               @MappingTarget Employee entity);


    protected Department departmentFromId(Long departmentId) {
        return departmentId == null ? null
            : departmentDao.findById(departmentId)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found with id "
                + departmentId));
    }

    protected Position positionFromId(Long positionId) {
        return positionId == null ? null
            : positionDao.findById(positionId)
            .orElseThrow(() -> new ResourceNotFoundException("Position not found with id "
                + positionId));
    }
}