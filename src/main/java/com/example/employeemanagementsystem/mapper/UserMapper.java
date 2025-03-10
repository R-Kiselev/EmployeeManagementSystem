package com.example.employeemanagementsystem.mapper;

import com.example.employeemanagementsystem.dto.create.UserCreateDto;
import com.example.employeemanagementsystem.dto.get.UserDto;
import com.example.employeemanagementsystem.model.Role;
import com.example.employeemanagementsystem.model.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    uses = {EmployeeMapper.class, RoleMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(source = "employeeId", target = "employee.id")
//    @Mapping(
//        source = "roleIds",
//        target = "roles",
//        qualifiedByName = "mapRoleIdsToRoles") // Используем qualifiedByName
//    User toEntity(UserCreateDto dto);
//
//    //  User -> UserDto  (УБИРАЕМ явный маппинг employeeId)
//    UserDto toDto(User user);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(source = "employeeId", target = "employee.id")
//    @Mapping(
//        source = "roleIds",
//        target = "roles",
//        qualifiedByName = "mapRoleIdsToRoles")
//    void updateUserFromDto(UserCreateDto dto, @MappingTarget User entity);
//
//    // Вспомогательный метод для маппинга Set<Long> (roleIds) в Set<Role>
//    @Named("mapRoleIdsToRoles")
//    default Set<Role> mapRoleIdsToRoles(Set<Long> roleIds) {
//        if (roleIds == null) {
//            return null;
//        }
//        // ВАЖНО:  В реальном приложении здесь нужна зависимость от RoleService!
//        //         Внедрять сервисы в мапперы - плохая практика.
//        //         Лучше перенести эту логику в UserService.
//        return roleIds.stream().map(id -> new Role(id, null, null)).collect(Collectors.toSet());
//    }
}