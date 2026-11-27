package com.joysistvi.employeemanagement.repository;

import com.joysistvi.employeemanagement.model.Position;
import java.sql.SQLException;
import java.util.List;

public interface PositionRepository {
    boolean save(Position position) throws SQLException;
    List<Position> findAll() throws SQLException;
    Position findById(int positionId) throws SQLException;
    boolean existsByName(String positionName) throws SQLException;
    boolean update(Position position) throws SQLException;
    boolean deleteById(int positionId) throws SQLException;
}
