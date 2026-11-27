package com.joysistvi.employeemanagement.repository;

import com.joysistvi.employeemanagement.config.DbConnection;
import com.joysistvi.employeemanagement.model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final DbConnection dbConnection = new DbConnection();

    @Override
    public boolean save(Department department) throws SQLException {
        String sql = "INSERT INTO departments (department_name, description) VALUES (?, ?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, department.getDepartmentName());
            preparedStatement.setString(2, department.getDescription());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public List<Department> findAll() throws SQLException {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT department_id, department_name, description FROM departments ORDER BY department_id ASC";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                list.add(mapRow(resultSet));
            }
        }
        return list;
    }

    @Override
    public Department findById(int departmentId) throws SQLException {
        String sql = "SELECT department_id, department_name, description FROM departments WHERE department_id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, departmentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? mapRow(resultSet) : null;
            }
        }
    }

    @Override
    public boolean existsByName(String departmentName) throws SQLException {
        String sql = "SELECT 1 FROM departments WHERE LOWER(department_name) = LOWER(?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, departmentName.trim());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public boolean update(Department department) throws SQLException {
        String sql = "UPDATE departments SET department_name = ?, description = ? WHERE department_id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, department.getDepartmentName());
            preparedStatement.setString(2, department.getDescription());
            preparedStatement.setInt(3, department.getDepartmentId());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(int departmentId) throws SQLException {
        String sql = "DELETE FROM departments WHERE department_id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, departmentId);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    private Department mapRow(ResultSet resultSet) throws SQLException {
        return new Department(
                resultSet.getInt("department_id"),
                resultSet.getString("department_name"),
                resultSet.getString("description")
        );
    }
}
