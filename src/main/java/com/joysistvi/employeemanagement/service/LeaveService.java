package com.joysistvi.employeemanagement.service;

import com.joysistvi.employeemanagement.model.Leave;
import java.time.LocalDate;
import java.util.List;

public interface LeaveService {
    String fileLeave(int employeeId, String leaveType, LocalDate startDate, LocalDate endDate, String reason);
    List<Leave> getAllLeaves();
    List<Leave> getLeavesByEmployee(int employeeId);
}
