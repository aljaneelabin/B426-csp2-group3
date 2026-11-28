package com.joysistvi.employeemanagement.repository;

import com.joysistvi.employeemanagement.model.Attendance;
import java.util.List;

public interface AttendanceRepository {

    boolean saveTimeIn(Attendance attendance);
    boolean updateTimeOut(int employeeId, int attendanceDate, Attendance timeOut);
    Attendance findByEmployeeAndDate(int employeeId, int attendanceDate);
    List<Attendance> findAll();
    List<Attendance> findByEmployeeId(int employeeId);

}
