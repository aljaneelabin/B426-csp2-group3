package com.joysistvi.employeemanagement.view;

import com.joysistvi.employeemanagement.model.Department;
import com.joysistvi.employeemanagement.repository.DepartmentRepositoryImpl;
import com.joysistvi.employeemanagement.service.DepartmentService;
import com.joysistvi.employeemanagement.service.DepartmentServiceImpl;

import java.util.List;
import java.util.Scanner;

public class DepartmentView {
    private final DepartmentService service;
    private final Scanner scanner;

    public DepartmentView(DepartmentService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public DepartmentView(DepartmentService service) {
        this(service, new Scanner(System.in));
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n================ DEPARTMENT MANAGEMENT ================");
            System.out.println("1. List All Departments");
            System.out.println("2. Add Department");
            System.out.println("3. Update Department");
            System.out.println("4. Delete Department");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1":
                        listDepartments();
                        break;
                    case "2":
                        addDepartment();
                        break;
                    case "3":
                        updateDepartment();
                        break;
                    case "4":
                        deleteDepartment();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Invalid option. Please enter 0-4.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private boolean listDepartments() {
        List<Department> departments = service.getAllDepartments();
        if (departments.isEmpty()) {
            System.out.println("No departments found.");
            return false;
        }
        System.out.println("\n-----------------------------------------------------------------------");
        System.out.printf("%-5s | %-25s | %-35s%n", "ID", "Department Name", "Description");
        System.out.println("-----------------------------------------------------------------------");
        for (Department d : departments) {
            System.out.printf("%-5d | %-25s | %-35s%n",
                    d.getDepartmentId(), d.getDepartmentName(), d.getDescription());
        }
        System.out.println("-----------------------------------------------------------------------");
        return true;
    }

    private void addDepartment() {
        System.out.println("(Type 'cancel' at any prompt to return to the menu)");

        String name;
        while (true) {
            System.out.print("Enter Department Name: ");
            name = scanner.nextLine().trim();
            if (name.equalsIgnoreCase("cancel")) {
                System.out.println("Action cancelled.");
                return;
            }
            if (name.isEmpty()) {
                System.out.println("Error: Department name cannot be empty.");
                continue;
            }
            if (service.isNameTaken(name)) {
                System.out.println("Error: Department '" + name + "' already exists.");
                continue;
            }
            break;
        }

        System.out.print("Enter Description: ");
        String desc = scanner.nextLine();
        if (desc.trim().equalsIgnoreCase("cancel")) {
            System.out.println("Action cancelled.");
            return;
        }

        service.addDepartment(name, desc);
        System.out.println("Department added successfully!");
    }

    private void updateDepartment() {
        if (!listDepartments()) return;
        System.out.println("(Type 'cancel' at any prompt to return to the menu)");

        Department current = promptExistingDepartment("Enter Department ID to update: ");
        if (current == null) return;

        String name;
        while (true) {
            System.out.print("New Department Name [" + current.getDepartmentName() + "]: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {
                System.out.println("Update cancelled.");
                return;
            }
            if (input.isEmpty()) {
                name = current.getDepartmentName();
                break;
            }
            if (!input.equalsIgnoreCase(current.getDepartmentName()) && service.isNameTaken(input)) {
                System.out.println("Error: Another department named '" + input + "' already exists.");
                continue;
            }
            name = input;
            break;
        }

        System.out.print("New Description [" + current.getDescription() + "]: ");
        String desc = scanner.nextLine();
        if (desc.trim().equalsIgnoreCase("cancel")) {
            System.out.println("Update cancelled.");
            return;
        }

        service.updateDepartment(current.getDepartmentId(), name, desc);
        System.out.println("Department updated successfully!");
    }

    private void deleteDepartment() {
        if (!listDepartments()) return;
        System.out.println("(Type 'cancel' at any prompt to return to the menu)");

        Department target = promptExistingDepartment("Enter Department ID to delete: ");
        if (target == null) return;

        while (true) {
            System.out.print("Are you sure you want to delete '" + target.getDepartmentName() + "'? (y/n): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("y")) {
                service.deleteDepartment(target.getDepartmentId());
                System.out.println("Department deleted successfully!");
                return;
            } else if (confirm.equalsIgnoreCase("n") || confirm.equalsIgnoreCase("cancel")) {
                System.out.println("Deletion cancelled.");
                return;
            } else {
                System.out.println("Error: Please enter 'y' or 'n'.");
            }
        }
    }

    private Department promptExistingDepartment(String promptMessage) {
        while (true) {
            System.out.print(promptMessage);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {
                System.out.println("Action cancelled.");
                return null;
            }
            try {
                int id = Integer.parseInt(input);
                return service.getDepartmentById(id);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric ID.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}

/*
    public static void main(String[] args) {
        DepartmentService testService = new DepartmentServiceImpl(new DepartmentRepositoryImpl());
        new DepartmentView(testService).showMenu();
    }
 */
