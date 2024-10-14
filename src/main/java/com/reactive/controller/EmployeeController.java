package com.reactive.controller;

import com.reactive.dto.EmployeeDto;
import com.reactive.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Mono<ResponseEntity<EmployeeDto>> createEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.addAemployee(employeeDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDto>> getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDto>> updateEmployee(
            @PathVariable String id, @RequestBody EmployeeDto employeeDto) {
        return employeeService.editAEmployee(id, employeeDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteEmployee(@PathVariable String id) {
        return employeeService.deleteEmployee(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

    // Endpoint to find employees by department
    @GetMapping("/department/{department}")
    public Flux<EmployeeDto> getEmployeesByDepartment(@PathVariable String department) {
        return employeeService.findEmployeesByDepartment(department);
    }

    // Endpoint to find employees by salary range
    @GetMapping("/salary-range")
    public Flux<EmployeeDto> getEmployeesBySalaryRange(@RequestParam double minSalary, @RequestParam double maxSalary) {
        return employeeService.findEmployeesBySalaryRange(minSalary, maxSalary);
    }

    // Endpoint to count employees by gender
    @GetMapping("/count/gender/{gender}")
    public Mono<ResponseEntity<Long>> countEmployeesByGender(@PathVariable String gender) {
        return employeeService.countEmployeesByGender(gender)
                .map(count -> ResponseEntity.ok(count))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
