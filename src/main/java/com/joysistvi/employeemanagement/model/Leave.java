package com.joysistvi.employeemanagement.model;

import java.time.LocalDate;

public class Leave {
    private int leaveId;
    private int employeeId;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String reason;

    private String employeeName;
    private int days;
    private String dateRequested;
    private boolean archived;

    public Leave() {
    }

    public Leave(int leaveId, int employeeId, String leaveType, LocalDate startDate, LocalDate endDate, String status, String reason) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.reason = reason;
    }

    public Leave(int leaveId, int employeeId, String employeeName, String leaveType, LocalDate startDate, LocalDate endDate,
                 int days, String reason, String status, String dateRequested) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.reason = reason;
        this.status = status;
        this.dateRequested = dateRequested;
    }

    public Leave(int employeeId, String leaveType, LocalDate startDate, LocalDate endDate, String reason) {
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }

    
    public int getId() { return leaveId; }
    
    public void setId(int id) { this.leaveId = id; }

    public int getEmployeeId() { return employeeId; }
    
    public String getLeaveType() { return leaveType; }

    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }

    public LocalDate getStartDate() { return startDate; }
    
    public LocalDate getEndDate() { return endDate; }
    
    public String getStatus() { return status; }
    
    public String getReason() { return reason; }
  
    public int getDays() { return days; }
    
    public void setArchived(boolean archived) { this.archived = archived; }
}
