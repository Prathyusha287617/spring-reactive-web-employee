package com.reactive.service;

import com.reactive.dto.EmployeeDto;
import com.reactive.entity.Employee;
import com.reactive.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository empRepo;

    //Convert EmployeeDto to Employee entity
    public Employee convertToEntity(EmployeeDto empDto) {
        return Employee.builder()
                .id(empDto.getId())
                .name(empDto.getName())
                .gender(empDto.getGender())
                .mobnum(empDto.getMobnum())
                .department(empDto.getDepartment())
                .branchcity(empDto.getBranchcity())
                .empsalary(empDto.getEmpsalary())
                .emphr(empDto.getEmphr())
                .build();
    }

    public EmployeeDto convertToDto(Employee emp) {
        return EmployeeDto.builder()
                .id(emp.getId())
                .name(emp.getName())
                .gender(emp.getGender())
                .mobnum(emp.getMobnum())
                .department(emp.getDepartment())
                .branchcity(emp.getBranchcity())
                .empsalary(emp.getEmpsalary())
                .emphr(emp.getEmphr())
                .build();
    }

    public Mono<EmployeeDto> addAemployee(EmployeeDto empDto) {
        Employee emp = convertToEntity(empDto);
        return empRepo.save(emp).map(this::convertToDto);
    }

    public Flux<EmployeeDto> getAllEmployee() {
        return this.empRepo.findAll().map(this::convertToDto);
    }

    public Mono<EmployeeDto> getEmployeeById(String id) {
        return this.empRepo.findById(id).map(this::convertToDto);
    }

    public Mono<EmployeeDto> editAEmployee(String id, EmployeeDto empDto) {
        return this.empRepo.findById(id)
                .flatMap(existingEmp -> {
                    existingEmp.setName(empDto.getName());
                    existingEmp.setGender(empDto.getGender());
                    existingEmp.setMobnum(empDto.getMobnum());
                    existingEmp.setDepartment(empDto.getDepartment());
                    existingEmp.setBranchcity(empDto.getBranchcity());
                    existingEmp.setEmpsalary(empDto.getEmpsalary());
                    existingEmp.setEmphr(empDto.getEmphr());
                    return empRepo.save(existingEmp);
                })
                .map(this::convertToDto) // Convert to EmployeeDto
                .switchIfEmpty(Mono.error(new ClassNotFoundException("Employee not found")));
    }

    public Mono<Void> deleteEmployee(String id) {
        return empRepo.deleteById(id);
    }

    // New method to find employees by department
    public Flux<EmployeeDto> findEmployeesByDepartment(String department) {
        return empRepo.findAll()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .map(this::convertToDto);
    }

    // New method to find employees by salary range
    public Flux<EmployeeDto> findEmployeesBySalaryRange(Number minSalary, Number maxSalary) {
        return empRepo.findAll()
                .filter(emp -> emp.getEmpsalary().doubleValue() >= minSalary.doubleValue() &&
                        emp.getEmpsalary().doubleValue() <= maxSalary.doubleValue())
                .map(this::convertToDto);
    }

    // New method to count employees by gender
    public Mono<Long> countEmployeesByGender(String gender) {
        return empRepo.findAll()
                .filter(emp -> emp.getGender().equalsIgnoreCase(gender))
                .count();
    }

}


