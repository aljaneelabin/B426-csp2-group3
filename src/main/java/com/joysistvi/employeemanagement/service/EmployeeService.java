package com.joysistvi.employeemanagement.service;

import com.joysistvi.employeemanagement.model.Employee;

import java.util.List;

public interface EmployeeService {

    void addEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(int employeeId);

    void updateEmployee(Employee employee);

    void archiveEmployee(int employeeId);
}
