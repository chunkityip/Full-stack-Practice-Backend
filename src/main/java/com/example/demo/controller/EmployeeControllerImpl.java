package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Employee Controller",
        description = "Handles all Employee CRUD operations (Create, Read, Update, Delete)"
)
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService employeeService;
    @Operation(summary = "Create a new employee record")
    @Override
    public ResponseEntity<Employee> createEmployee(Employee payload) {
        return ResponseEntity.ok(employeeService.createEmployee(payload));
    }

    @Operation(summary = "Retrieve an employee by ID")
    @Override
    public ResponseEntity<Optional<Employee>> getEmployeeById(Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(summary = "Retrieve all employees")
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(summary = "Update existing employee by ID")
    @Override
    public ResponseEntity<Employee> updateEmployee(Long id, Employee payload) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, payload));
    }

    @Operation(summary = "Delete an employee by ID")
    @Override
    public ResponseEntity<Void> deleteEmployee(Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}