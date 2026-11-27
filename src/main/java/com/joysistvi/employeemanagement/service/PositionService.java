package com.joysistvi.employeemanagement.service;

import com.joysistvi.employeemanagement.model.Position;
import java.util.List;

public interface PositionService {
    void addPosition(String name, String description, double salary);
    List<Position> getAllPositions();
    Position getPositionById(int positionId);
    void updatePosition(int positionId, String name, String description, double salary);
    void deletePosition(int positionId);
    boolean isNameTaken(String name);
}
