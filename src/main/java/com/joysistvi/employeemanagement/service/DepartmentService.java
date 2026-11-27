package com.joysistvi.employeemanagement.service;

import com.joysistvi.employeemanagement.model.Department;
import java.util.List;

public interface DepartmentService {
    void addDepartment(String name, String description);
    List<Department> getAllDepartments();
    Department getDepartmentById(int departmentId);
    void updateDepartment(int departmentId, String name, String description);
    void deleteDepartment(int departmentId);
    boolean isNameTaken(String name);
}
