// ./dto/create/EmployeeCreateDto.java
package com.example.employeemanagementsystem.dto.create;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCreateDto {

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Hire date cannot be null")
    private LocalDate hireDate;

    @NotNull(message = "Salary cannot be null")
    @Positive(message = "Salary must be positive")
    private BigDecimal salary;

    private Boolean isActive;

    private Long departmentId;

    private Long positionId;
    private Long userId;
}