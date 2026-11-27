package com.joysistvi.employeemanagement.service;

import com.joysistvi.employeemanagement.config.DbConnection;
import com.joysistvi.employeemanagement.model.Department;
import com.joysistvi.employeemanagement.repository.DepartmentRepository;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository repository;

    public DepartmentServiceImpl(DepartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addDepartment(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty.");
        }
        try {
            String cleanName = name.trim();
            if (repository.existsByName(cleanName)) {
                throw new IllegalArgumentException("Department '" + cleanName + "' already exists.");
            }
            String cleanDesc = (description == null || description.trim().isEmpty()) ? "No description" : description.trim();
            repository.save(new Department(cleanName, cleanDesc));
        } catch (SQLException e) {
            throw new RuntimeException("Database error while adding department: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Department> getAllDepartments() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching departments: " + e.getMessage(), e);
        }
    }

    @Override
    public Department getDepartmentById(int departmentId) {
        try {
            Department dept = repository.findById(departmentId);
            if (dept == null) {
                throw new IllegalArgumentException("Department ID " + departmentId + " not found.");
            }
            return dept;
        } catch (SQLException e) {
            throw new RuntimeException("Database error while finding department: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateDepartment(int departmentId, String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name cannot be empty.");
        }
        try {
            Department existing = repository.findById(departmentId);
            if (existing == null) {
                throw new IllegalArgumentException("Department ID " + departmentId + " does not exist.");
            }
            String cleanName = name.trim();
            if (!existing.getDepartmentName().equalsIgnoreCase(cleanName) && repository.existsByName(cleanName)) {
                throw new IllegalArgumentException("Another department named '" + cleanName + "' already exists.");
            }
            String cleanDesc = (description == null || description.trim().isEmpty()) ? existing.getDescription() : description.trim();
            repository.update(new Department(departmentId, cleanName, cleanDesc));
        } catch (SQLException e) {
            throw new RuntimeException("Database error while updating department: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteDepartment(int departmentId) {
        try {
            if (!repository.deleteById(departmentId)) {
                throw new IllegalArgumentException("Department ID " + departmentId + " not found.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalStateException("Cannot delete department: employees are still assigned to it.");
        } catch (SQLException e) {
            throw new RuntimeException("Database error while deleting department: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean isNameTaken(String name) {
        try {
            return repository.existsByName(name.trim());
        } catch (SQLException e) {
            throw new RuntimeException(DbConnection.friendlyMessage(e), e);
        }
    }
}
