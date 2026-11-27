package com.joysistvi.employeemanagement.view;

import com.joysistvi.employeemanagement.model.Position;
import com.joysistvi.employeemanagement.repository.PositionRepositoryImpl;
import com.joysistvi.employeemanagement.service.PositionService;
import com.joysistvi.employeemanagement.service.PositionServiceImpl;

import java.util.List;
import java.util.Scanner;

public class PositionView {
    private final PositionService service;
    private final Scanner scanner;

    public PositionView(PositionService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public PositionView(PositionService service) {
        this(service, new Scanner(System.in));
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n================= POSITION MANAGEMENT =================");
            System.out.println("1. List All Positions");
            System.out.println("2. Add Position");
            System.out.println("3. Update Position");
            System.out.println("4. Delete Position");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1":
                        listPositions();
                        break;
                    case "2":
                        addPosition();
                        break;
                    case "3":
                        updatePosition();
                        break;
                    case "4":
                        deletePosition();
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

    private boolean listPositions() {
        List<Position> positions = service.getAllPositions();
        if (positions.isEmpty()) {
            System.out.println("No positions found.");
            return false;
        }
        System.out.println("\n------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-25s | %-15s | %-30s%n", "ID", "Position Name", "Base Salary", "Description");
        System.out.println("------------------------------------------------------------------------------------");
        for (Position p : positions) {
            System.out.printf("%-5d | %-25s | PHP %11.2f | %-30s%n",
                    p.getPositionId(), p.getPositionName(), p.getSalary(), p.getDescription());
        }
        System.out.println("------------------------------------------------------------------------------------");
        return true;
    }

    private void addPosition() {
        System.out.println("(Type 'cancel' at any prompt to return to the menu)");

        String name;
        while (true) {
            System.out.print("Enter Position Name: ");
            name = scanner.nextLine().trim();
            if (name.equalsIgnoreCase("cancel")) {
                System.out.println("Action cancelled.");
                return;
            }
            if (name.isEmpty()) {
                System.out.println("Error: Position name cannot be empty.");
                continue;
            }
            if (service.isNameTaken(name)) {
                System.out.println("Error: Position '" + name + "' already exists.");
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

        Double salary = promptValidSalary("Enter Base Salary (PHP): ", null);
        if (salary == null) return;

        service.addPosition(name, desc, salary);
        System.out.println("Position added successfully!");
    }

    private void updatePosition() {
        if (!listPositions()) return;
        System.out.println("(Type 'cancel' at any prompt to return to the menu)");

        Position current = promptExistingPosition("Enter Position ID to update: ");
        if (current == null) return;

        String name;
        while (true) {
            System.out.print("New Position Name [" + current.getPositionName() + "]: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {
                System.out.println("Update cancelled.");
                return;
            }
            if (input.isEmpty()) {
                name = current.getPositionName();
                break;
            }
            if (!input.equalsIgnoreCase(current.getPositionName()) && service.isNameTaken(input)) {
                System.out.println("Error: Another position named '" + input + "' already exists.");
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

        Double salary = promptValidSalary(
                "New Salary [" + String.format("%.2f", current.getSalary()) + "]: ",
                current.getSalary()
        );
        if (salary == null) return;

        service.updatePosition(current.getPositionId(), name, desc, salary);
        System.out.println("Position updated successfully!");
    }

    private void deletePosition() {
        if (!listPositions()) return;
        System.out.println("(Type 'cancel' at any prompt to return to the menu)");

        Position target = promptExistingPosition("Enter Position ID to delete: ");
        if (target == null) return;

        while (true) {
            System.out.print("Are you sure you want to delete '" + target.getPositionName() + "'? (y/n): ");
            String confirm = scanner.nextLine().trim();
            if (confirm.equalsIgnoreCase("y")) {
                service.deletePosition(target.getPositionId());
                System.out.println("Position deleted successfully!");
                return;
            } else if (confirm.equalsIgnoreCase("n") || confirm.equalsIgnoreCase("cancel")) {
                System.out.println("Deletion cancelled.");
                return;
            } else {
                System.out.println("Error: Please enter 'y' or 'n'.");
            }
        }
    }

    private Position promptExistingPosition(String promptMessage) {
        while (true) {
            System.out.print(promptMessage);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {
                System.out.println("Action cancelled.");
                return null;
            }
            try {
                int id = Integer.parseInt(input);
                return service.getPositionById(id);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric ID.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private Double promptValidSalary(String promptMessage, Double defaultValue) {
        while (true) {
            System.out.print(promptMessage);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {
                System.out.println("Action cancelled.");
                return null;
            }
            if (input.isEmpty() && defaultValue != null) {
                return defaultValue;
            }
            try {
                double salary = Double.parseDouble(input);
                if (salary <= 0) {
                    System.out.println("Error: Salary must be greater than zero.");
                    continue;
                }
                return salary;
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid numeric amount for salary.");
            }
        }
    }


}

/*
    public static void main(String[] args) {
        PositionService testService = new PositionServiceImpl(new PositionRepositoryImpl());
        new PositionView(testService).showMenu();
    }
 */
