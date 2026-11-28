package com.joysistvi.employeemanagement.model;

public class Attendance {
    private int attendanceId;
    private int employeeId;
    private int attendanceDate;
    private int timeIn;
    private int timeOut;
    private String status;

    public Attendance() {
    }

    public Attendance(int attendanceId, int employeeId, int attendanceDate, int timeIn, int timeOut, String status) {
        this.attendanceId = attendanceId;
        this.employeeId = employeeId;
        this.attendanceDate = attendanceDate;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.status = status;
    }

    public int getAttendanceId() { return attendanceId; }
    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public int getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(int attendanceDate) { this.attendanceDate = attendanceDate; }

    public int getTimeIn() { return timeIn; }
    public void setTimeIn(int timeIn) { this.timeIn = timeIn; }

    public int getTimeOut() { return timeOut; }
    public void setTimeOut(int timeOut) { this.timeOut = timeOut; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
