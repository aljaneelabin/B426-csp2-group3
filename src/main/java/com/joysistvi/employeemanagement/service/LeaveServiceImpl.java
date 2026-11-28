package com.joysistvi.employeemanagement.service;

import com.joysistvi.employeemanagement.model.Leave;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeaveServiceImpl implements LeaveService {

    private final List<Leave> tempLeaveStore = new ArrayList<>();

    public LeaveServiceImpl() {
    }

    @Override
    public String fileLeave(int employeeId, String leaveType, LocalDate startDate, LocalDate endDate, String reason) {
        if (employeeId <= 0) {
            return "Employee ID must be greater than 0.";
        }

        if (leaveType == null || leaveType.isBlank()) {
            return "Leave type is required.";
        }

        if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
            return "End date cannot be before start date.";
        }

        if (reason == null || reason.isBlank()) {
            return "Please provide a reason for the leave.";
        }

        Leave leave = new Leave(
                tempLeaveStore.size() + 1,
                employeeId,
                leaveType.trim(),
                startDate,
                endDate,
                "PENDING",
                reason.trim()
        );
        tempLeaveStore.add(leave);
        return "Leave request submitted (ID: " + leave.getLeaveType() + ", Status: PENDING).";
    }

    @Override
    public List<Leave> getAllLeaves() {
        return new ArrayList<>(tempLeaveStore);
    }

    @Override
    public List<Leave> getLeavesByEmployee(int employeeId) {
        return tempLeaveStore.stream()
                .filter(l -> l.getEmployeeId() == employeeId)
                .toList();
    }
}