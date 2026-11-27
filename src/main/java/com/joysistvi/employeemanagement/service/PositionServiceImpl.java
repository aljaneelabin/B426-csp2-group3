package com.joysistvi.employeemanagement.service;

import com.joysistvi.employeemanagement.config.DbConnection;
import com.joysistvi.employeemanagement.model.Position;
import com.joysistvi.employeemanagement.repository.PositionRepository;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class PositionServiceImpl implements PositionService {
    private final PositionRepository repository;

    public PositionServiceImpl(PositionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addPosition(String name, String description, double salary) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Position name cannot be empty.");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be greater than zero.");
        }
        try {
            String cleanName = name.trim();
            if (repository.existsByName(cleanName)) {
                throw new IllegalArgumentException("Position '" + cleanName + "' already exists.");
            }
            String cleanDesc = (description == null || description.trim().isEmpty()) ? "No description" : description.trim();
            repository.save(new Position(cleanName, cleanDesc, salary));
        } catch (SQLException e) {
            throw new RuntimeException("Database error while adding position: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Position> getAllPositions() {
        try {
            return repository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching positions: " + e.getMessage(), e);
        }
    }

    @Override
    public Position getPositionById(int positionId) {
        try {
            Position pos = repository.findById(positionId);
            if (pos == null) {
                throw new IllegalArgumentException("Position ID " + positionId + " not found.");
            }
            return pos;
        } catch (SQLException e) {
            throw new RuntimeException("Database error while finding position: " + e.getMessage(), e);
        }
    }

    @Override
    public void updatePosition(int positionId, String name, String description, double salary) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Position name cannot be empty.");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("Salary must be greater than zero.");
        }
        try {
            Position existing = repository.findById(positionId);
            if (existing == null) {
                throw new IllegalArgumentException("Position ID " + positionId + " does not exist.");
            }
            String cleanName = name.trim();
            if (!existing.getPositionName().equalsIgnoreCase(cleanName) && repository.existsByName(cleanName)) {
                throw new IllegalArgumentException("Another position named '" + cleanName + "' already exists.");
            }
            String cleanDesc = (description == null || description.trim().isEmpty()) ? existing.getDescription() : description.trim();
            repository.update(new Position(positionId, cleanName, cleanDesc, salary));
        } catch (SQLException e) {
            throw new RuntimeException("Database error while updating position: " + e.getMessage(), e);
        }
    }

    @Override
    public void deletePosition(int positionId) {
        try {
            if (!repository.deleteById(positionId)) {
                throw new IllegalArgumentException("Position ID " + positionId + " not found.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalStateException("Cannot delete position: employees are still assigned to it.");
        } catch (SQLException e) {
            throw new RuntimeException("Database error while deleting position: " + e.getMessage(), e);
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
