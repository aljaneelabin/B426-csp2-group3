package com.joysistvi.employeemanagement.repository;

import com.joysistvi.employeemanagement.model.Attendance;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRepositoryImpl implements AttendanceRepository {
    private List<Attendance> attendanceList = new ArrayList<>();

    @Override
    public boolean saveTimeIn(Attendance attendance) {
        return attendanceList.add(attendance);
    }

    @Override
    public boolean updateTimeOut(int employeeId, int attendanceDate, Attendance timeOut) {
        for (int i = 0; i < attendanceList.size(); i++) {
            Attendance a = attendanceList.get(i);
            if (a.getEmployeeId() == employeeId && a.getAttendanceDate() == attendanceDate) {
                a.setTimeOut(timeOut);
                return true;
            }
        }
        return false;
    }

    @Override
    public Attendance findByEmployeeAndDate(int employeeId, int attendanceDate) {
        for (int i = 0; i < attendanceList.size(); i++) {
            Attendance a = attendanceList.get(i);
            if (a.getEmployeeId() == employeeId && a.getAttendanceDate() == attendanceDate) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<Attendance> findAll() {
        return attendanceList;
    }

    @Override
    public List<Attendance> findByEmployeeId(int employeeId) {
        List<Attendance> result = new ArrayList<>();
        for (int i = 0; i < attendanceList.size(); i++) {
            Attendance a = attendanceList.get(i);
            if (a.getEmployeeId() == employeeId) {
                result.add(a);
            }
        }
        return result;
    }
}
