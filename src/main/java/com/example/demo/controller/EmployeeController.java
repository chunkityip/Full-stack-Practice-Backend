package com.example.demo.controller;

import com.example.demo.entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/employees")
public interface EmployeeController {

    @PostMapping
    ResponseEntity<Employee> createEmployee(@RequestBody Employee payload);

    @GetMapping("/{id}")
    ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<Employee>> getAllEmployees();

    @PutMapping("/{id}")
    ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                            @RequestBody Employee payload);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEmployee(@PathVariable Long id);
}