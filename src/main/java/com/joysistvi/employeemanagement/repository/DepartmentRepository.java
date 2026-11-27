package com.joysistvi.employeemanagement.repository;

import com.joysistvi.employeemanagement.model.Department;
import java.sql.SQLException;
import java.util.List;

public interface DepartmentRepository {
    boolean save(Department department) throws SQLException;
    List<Department> findAll() throws SQLException;
    Department findById(int departmentId) throws SQLException;
    boolean existsByName(String departmentName) throws SQLException;
    boolean update(Department department) throws SQLException;
    boolean deleteById(int departmentId) throws SQLException;
}
