package com.joysistvi.employeemanagement.view;

import com.joysistvi.employeemanagement.model.Attendance;
import com.joysistvi.employeemanagement.service.AttendanceService;
import java.util.List;
import java.util.Scanner;

public class AttendanceView {
    private final AttendanceService attendanceService;
    private final Scanner scanner;

    public AttendanceView(AttendanceService attendanceService, Scanner scanner) {
        this.attendanceService = attendanceService;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== ATTENDANCE MENU ===");
            System.out.println("1. Record Time-In");
            System.out.println("2. Record Time-Out");
            System.out.println("3. View All Attendance");
            System.out.println("4. View Attendance by Employee ID");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> handleTimeIn();
                case "2" -> handleTimeOut();
                case "3" -> printAttendanceList(attendanceService.viewAllAttendance());
                case "4" -> handleViewByEmployee();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void handleTimeIn() {
        System.out.print("Enter Employee ID: ");
        int empId = readInt();
        if (empId == -1) {
            return;
        }

        System.out.print("Enter Attendance Date (e.g., 20261128): ");
        int date = readInt();
        if (date == -1) {
            return;
        }

        System.out.print("Enter Time-In (e.g., 830 or 915): ");
        int timeIn = readInt();
        if (timeIn == -1) {
            return;
        }

        System.out.println(attendanceService.recordTimeIn(empId, date, timeIn));
    }

    private void handleTimeOut() {
        System.out.print("Enter Employee ID: ");
        int empId = readInt();
        if (empId == -1) {
            return;
        }

        System.out.print("Enter Attendance Date (e.g., 20261128): ");
        int date = readInt();
        if (date == -1) {
            return;
        }

        System.out.print("Enter Time-Out (e.g., 1700): ");
        int timeOut = readInt();
        if (timeOut == -1) {
            return;
        }

        System.out.println(attendanceService.recordTimeOut(empId, date, timeOut));
    }

    private void handleViewByEmployee() {
        System.out.print("Enter Employee ID: ");
        int empId = readInt();
        if (empId != -1) {
            printAttendanceList(attendanceService.viewAttendanceByEmployee(empId));
        }
    }

    private void printAttendanceList(List<Attendance> list) {
        if (list.isEmpty()) {
            System.out.println("No attendance records found.");
            return;
        }

        System.out.println("\nID | EmpID | Date     | Time-In | Time-Out | Status");
        for (Attendance a : list) {
            String timeOutDisplay = a.getTimeOut() == 0 ? "--:--:--" : String.valueOf(a.getTimeOut());
            System.out.printf("%d  | %d     | %d | %d      | %s | %s%n",
                    a.getAttendanceId(),
                    a.getEmployeeId(),
                    a.getAttendanceDate(),
                    a.getTimeIn(),
                    timeOutDisplay,
                    a.getStatus());
        }
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return -1;
        }
    }
}