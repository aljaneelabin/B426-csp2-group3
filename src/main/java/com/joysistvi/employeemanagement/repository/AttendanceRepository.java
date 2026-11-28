package com.joysistvi.employeemanagement.repository;

import com.joysistvi.employeemanagement.model.Attendance;
import java.util.List;

public interface AttendanceRepository {

    boolean saveTimeIn(Attendance attendance);
<<<<<<< HEAD
    boolean updateTimeOut(int employeeId, int attendanceDate, int timeOut);
=======
    boolean updateTimeOut(int employeeId, int attendanceDate, Attendance timeOut);

    boolean updateTimeOut(int employeeId, int attendanceDate, int timeOut);

>>>>>>> 276ccd2 (Updated leave and attendance model, attendance repo, attendance and leave services and attendance and leave view.)
    Attendance findByEmployeeAndDate(int employeeId, int attendanceDate);
    List<Attendance> findAll();
    List<Attendance> findByEmployeeId(int employeeId);

}
