package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.enums.EmployeeStatus;
import com.example.demo.repo.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;

    public EmployeeServiceImpl(EmployeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return repo.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return repo.findById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        return repo.findById(id).map(existing -> {
            existing.setFullName(employee.getFullName());
            existing.setDepartment(employee.getDepartment());
            return repo.save(existing);
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        repo.deleteById(id);
    }

    @Override
    public Employee submitForApproval(Long id) {
        return repo.findById(id).map(existing -> {
            if (existing.getStatus() != EmployeeStatus.DRAFT) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Only DRAFT employees can be submitted for approval");
            }
            existing.setStatus(EmployeeStatus.PENDING_APPROVAL);
            return repo.save(existing);
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    @Override
    public Employee approve(Long id) {
        return repo.findById(id).map(existing -> {
            if (existing.getStatus() != EmployeeStatus.PENDING_APPROVAL) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Only PENDING_APPROVAL employees can be approved");
            }
            existing.setStatus(EmployeeStatus.APPROVED);
            return repo.save(existing);
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    @Override
    public Employee reject(Long id) {
        return repo.findById(id).map(existing -> {
            if (existing.getStatus() != EmployeeStatus.PENDING_APPROVAL) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Only PENDING_APPROVAL employees can be rejected");
            }
            existing.setStatus(EmployeeStatus.REJECTED);
            return repo.save(existing);
        }).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }
}