package com.reactive.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {
    private String id;
    @NotBlank(message = "employee name is required")
    private String name;
    @NotBlank(message="employee gender is required")
    private String gender;
    @NotBlank(message = "employee phone number is required")
    private String mobnum;
    @NotBlank(message="employee department is required")
    private String department;
    @NotBlank(message="employee city details is required")
    private String branchcity;
    @Min(value=8000,message = "employee salary should be greater than 8000")
    private Number empsalary;
    @NotBlank(message = "employee hr details is required")
    private String emphr;
}
