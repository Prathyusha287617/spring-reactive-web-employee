package com.reactive.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "employeedb")
@Builder
public class Employee {
    @Id
    private String id;
    private String name;
    private String gender;
    private String mobnum;
    private String department;
    private String branchcity;
    private Number empsalary;
    private String emphr;
}
