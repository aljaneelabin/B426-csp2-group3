package com.joysistvi.employeemanagement.view;

import com.joysistvi.employeemanagement.model.Leave;
import com.joysistvi.employeemanagement.service.LeaveService;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class LeaveView {
    private final LeaveService leaveService;
    private final Scanner scanner;

    public LeaveView(LeaveService leaveService, Scanner scanner) {
        this.leaveService = leaveService;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=== LEAVE MENU ===");
            System.out.println("1. File Leave Request");
            System.out.println("2. View All Leave Requests");
            System.out.println("3. View Leave Requests by Employee ID");
            System.out.println("0. Back");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> handleFileLeave();
                case "2" -> printLeaves(leaveService.getAllLeaves());
                case "3" -> handleViewByEmployee();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void handleFileLeave() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Leave Type (e.g., Sick, Vacation, Emergency): ");
            String type = scanner.nextLine().trim();

            System.out.print("Start Date (YYYY-MM-DD): ");
            LocalDate start = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("End Date (YYYY-MM-DD): ");
            LocalDate end = LocalDate.parse(scanner.nextLine().trim());

            System.out.print("Reason: ");
            String reason = scanner.nextLine().trim();

            System.out.println(leaveService.fileLeave(empId, type, start, end, reason));
        } catch (NumberFormatException e) {
            System.out.println("Invalid Employee ID.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    private void handleViewByEmployee() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = Integer.parseInt(scanner.nextLine().trim());
            printLeaves(leaveService.getLeavesByEmployee(empId));
        } catch (NumberFormatException e) {
            System.out.println("Invalid Employee ID.");
        }
    }

    private void printLeaves(List<Leave> leaves) {
        if (leaves.isEmpty()) {
            System.out.println("No leave records found.");
            return;
        }
        System.out.println("\nID | EmpID | Type     | Start      | End        | Status  | Reason");
        for (Leave l : leaves) {
            System.out.printf("%d  | %d     | %-8s | %s | %s | %-7s | %s%n",
                    l.getLeaveType(),
                    l.getEmployeeId(),
                    l.getLeaveType(),
                    l.getStartDate(),
                    l.getEndDate(),
                    l.getStatus(),
                    l.getReason());
        }
    }
}