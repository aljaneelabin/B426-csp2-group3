package com.joysistvi.employeemanagement.service;

import com.joysistvi.employeemanagement.model.Attendance;
import com.joysistvi.employeemanagement.repository.AttendanceRepository;
import java.util.List;
import java.util.Optional;

public class AttendanceServiceImpl implements AttendanceService {
    private static final int NO_TIME_OUT = 0;
    private static final int LATE_THRESHOLD = 900; // e.g., 0900 (9:00 AM) if using HHmm int format

    private final AttendanceRepository attendanceRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public String recordTimeIn(int employeeId, int attendanceDate, int timeIn) {
        if (employeeId <= 0) {
            return "Invalid employee ID.";
        }
        if (attendanceDate <= 0) {
            return "Invalid attendance date.";
        }

        Optional<Attendance> existing = Optional.ofNullable(attendanceRepository.findByEmployeeAndDate(employeeId, attendanceDate));
        if (existing.isPresent()) {
            return "Time-in already recorded for Employee #" + employeeId + " on " + attendanceDate + ".";
        }

        String status = timeIn > LATE_THRESHOLD ? "LATE" : "PRESENT";
        int nextId = attendanceRepository.findAll().size() + 1;

        Attendance record = new Attendance(nextId, employeeId, attendanceDate, timeIn, NO_TIME_OUT, status);
        boolean saved = attendanceRepository.saveTimeIn(record);
        return saved
                ? "Time-in recorded at " + timeIn + " (" + status + ")."
                : "Failed to record time-in.";
    }

    @Override
    public String recordTimeOut(int employeeId, int attendanceDate, int timeOut) {
        if (employeeId <= 0) {
            return "Invalid employee ID.";
        }
        if (attendanceDate <= 0) {
            return "Invalid attendance date.";
        }

        Optional<Attendance> existingOpt = Optional.ofNullable(attendanceRepository.findByEmployeeAndDate(employeeId, attendanceDate));
        if (existingOpt.isEmpty()) {
            return "No time-in record found for Employee #" + employeeId + " on " + attendanceDate + ".";
        }

        Attendance record = existingOpt.get();
        if (record.getTimeOut() != NO_TIME_OUT) {
            return "Time-out already recorded at " + record.getTimeOut() + ".";
        }

        record.setTimeOut(timeOut);
        boolean updated = attendanceRepository.updateTimeOut(employeeId, attendanceDate, timeOut);
        return updated
                ? "Time-out recorded at " + timeOut + "."
                : "Failed to record time-out.";
    }

    @Override
    public List<Attendance> viewAllAttendance() {
        return attendanceRepository.findAll();
    }

    @Override
    public List<Attendance> viewAttendanceByEmployee(int employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }
}