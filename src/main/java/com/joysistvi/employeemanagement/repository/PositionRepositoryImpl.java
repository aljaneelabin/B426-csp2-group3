package com.joysistvi.employeemanagement.repository;

import com.joysistvi.employeemanagement.config.DbConnection;
import com.joysistvi.employeemanagement.model.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PositionRepositoryImpl implements PositionRepository {

    private final DbConnection dbConnection = new DbConnection();

    @Override
    public boolean save(Position position) throws SQLException {
        String sql = "INSERT INTO positions (position_name, description, salary) VALUES (?, ?, ?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, position.getPositionName());
            preparedStatement.setString(2, position.getDescription());
            preparedStatement.setDouble(3, position.getSalary());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public List<Position> findAll() throws SQLException {
        List<Position> list = new ArrayList<>();
        String sql = "SELECT position_id, position_name, description, salary FROM positions ORDER BY position_id ASC";
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
    public Position findById(int positionId) throws SQLException {
        String sql = "SELECT position_id, position_name, description, salary FROM positions WHERE position_id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, positionId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? mapRow(resultSet) : null;
            }
        }
    }

    @Override
    public boolean existsByName(String positionName) throws SQLException {
        String sql = "SELECT 1 FROM positions WHERE LOWER(position_name) = LOWER(?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, positionName.trim());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    @Override
    public boolean update(Position position) throws SQLException {
        String sql = "UPDATE positions SET position_name = ?, description = ?, salary = ? WHERE position_id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, position.getPositionName());
            preparedStatement.setString(2, position.getDescription());
            preparedStatement.setDouble(3, position.getSalary());
            preparedStatement.setInt(4, position.getPositionId());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(int positionId) throws SQLException {
        String sql = "DELETE FROM positions WHERE position_id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, positionId);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    private Position mapRow(ResultSet resultSet) throws SQLException {
        return new Position(
                resultSet.getInt("position_id"),
                resultSet.getString("position_name"),
                resultSet.getString("description"),
                resultSet.getDouble("salary")
        );
    }
}
