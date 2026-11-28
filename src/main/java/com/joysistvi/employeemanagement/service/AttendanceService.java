package com.joysistvi.employeemanagement.service;

import com.joysistvi.employeemanagement.model.Attendance;
import java.util.List;

public interface AttendanceService {
    String recordTimeIn(int employeeId, int attendanceDate, int timeIn);
    String recordTimeOut(int employeeId, int attendanceDate, int timeOut);
    List<Attendance> viewAllAttendance();
    List<Attendance> viewAttendanceByEmployee(int employeeId);

}