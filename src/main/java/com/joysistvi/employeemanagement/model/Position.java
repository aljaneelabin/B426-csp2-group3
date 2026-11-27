package com.joysistvi.employeemanagement.model;

public class Position {
    private int positionId;
    private String positionName;
    private String description;
    private double salary;

    public Position() {}

    public Position(int positionId, String positionName, String description, double salary) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.description = description;
        this.salary = salary;
    }

    public Position(String positionName, String description, double salary) {
        this.positionName = positionName;
        this.description = description;
        this.salary = salary;
    }

    public int getPositionId() { return positionId; }
    public void setPositionId(int positionId) { this.positionId = positionId; }

    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
}
