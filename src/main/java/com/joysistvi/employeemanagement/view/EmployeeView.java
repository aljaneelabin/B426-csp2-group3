package com.joysistvi.employeemanagement.view;

import com.joysistvi.employeemanagement.model.Employee;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeView {

    private final Scanner scanner = new Scanner(System.in);

    public void displayEmployeeMenu() {
        System.out.println("\n================ EMPLOYEE MANAGEMENT ================");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Search Employee");
        System.out.println("4. Update Employee");
        System.out.println("5. Archive Employee");
        System.out.println("0. Back");
    }

    public int getMenuChoice() {
        while (true) {
            System.out.print("Enter your choice: ");

            try {
                return Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number.");
            }
        }
    }

    public Employee getEmployeeInput() {

        System.out.println("\n===== ADD NEW EMPLOYEE =====");

        String firstName = getRequiredInput("First Name: ");
        System.out.print("Middle Name (optional): ");
        String middleName = scanner.nextLine().trim();
        if (middleName.isEmpty()) {
            middleName = null;
        }
        String lastName = getRequiredInput("Last Name: ");
        LocalDate birthday = getValidBirthday();
        String contactNumber = getValidContactNumber();
        String email = getValidEmail();
        LocalDate hireDate = getValidHireDate(birthday);
        int departmentId = getPositiveNumber("Department ID: ");
        int positionId = getPositiveNumber("Position ID: ");
        String address = getRequiredInput("Address: ");
        String employmentStatus = getRequiredInput("Employment Status: ");

        return new Employee(0, firstName, middleName, lastName, birthday, contactNumber, email, hireDate, departmentId, positionId, address, employmentStatus, 0, false);
    }

    public Employee getEmployeeUpdateInput(Employee employee) {

        System.out.println("\n===== UPDATE EMPLOYEE =====");

        String firstName = getRequiredInput("First Name: ");

        System.out.print("Middle Name (optional): ");
        String middleName = scanner.nextLine().trim();

        if (middleName.isEmpty()) {
            middleName = null;
        }

        String lastName = getRequiredInput("Last Name: ");
        LocalDate birthday = getValidBirthday();
        String contactNumber = getValidContactNumber();
        String email = getValidEmail();
        LocalDate hireDate = getValidHireDate(birthday);

        int departmentId = getPositiveNumber("Department ID: ");
        int positionId = getPositiveNumber("Position ID: ");

        String address = getRequiredInput("Address: ");
        String employmentStatus = getRequiredInput("Employment Status: ");

        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setLastName(lastName);
        employee.setBirthday(birthday);
        employee.setContactNumber(contactNumber);
        employee.setEmail(email);
        employee.setHireDate(hireDate);
        employee.setDepartmentId(departmentId);
        employee.setPositionId(positionId);
        employee.setAddress(address);
        employee.setEmploymentStatus(employmentStatus);

        return employee;
    }

    public boolean getArchiveConfirmation() {

        System.out.println("\n===== ARCHIVE EMPLOYEE =====");
        System.out.print("Are you sure you want to archive this employee? (Y/N): ");

        String answer = scanner.nextLine().trim();

        return answer.equalsIgnoreCase("Y");
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public int getSearchEmployeeId() {
        System.out.println("\n===== SEARCH EMPLOYEE =====");
        return getPositiveNumber("Enter Employee ID to search: ");
    }

    public void displayEmployeeDetails(Employee employee){
        System.out.println("Employee ID: " + employee.getId());
        System.out.println("First Name: " + employee.getFirstName());
        System.out.println("Middle Name: " + employee.getMiddleName());
        System.out.println("Last Name: " + employee.getLastName());
        System.out.println("Birthday: " + employee.getBirthday());
        System.out.println("Contact Number: " + employee.getContactNumber());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Hire Date: " + employee.getHireDate());
        System.out.println("Department ID: " + employee.getDepartmentId());
        System.out.println("Position ID: " + employee.getPositionId());
        System.out.println("Address: " + employee.getAddress());
        System.out.println("Employment Status: " + employee.getEmploymentStatus());
        System.out.println("Leave Credits: " + employee.getLeaveCredits());
        System.out.println("Archived: " + employee.isArchived());
    }

    public int getEmployeeIdInput() {
        return getPositiveNumber("Enter Employee ID: ");
    }

    private String getRequiredInput(String message) {
        String input;

        do {
            System.out.print(message);
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("This field cannot be empty. Please try again.");
            }

        } while (input.isEmpty());

        return input;
    }

    private String getValidEmail() {

        String email;

        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();

            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please try again.");

            } else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                System.out.println("Invalid email format. Please enter a valid email.");

            } else {
                return email;
            }
        }
    }

    private LocalDate getValidBirthday () {
        LocalDate birthday;

        while (true) {
            System.out.print("Birthday (YYYY-MM-DD): ");

            try {
                birthday = LocalDate.parse(scanner.nextLine());

                if (birthday.isAfter(LocalDate.now())) {
                    System.out.println("Birthday cannot be in the future.");
                } else {
                    return birthday;
                }

            } catch (DateTimeException e) {
                System.out.println("Invalid date please use YYYY-MM-DD.");
            }
        }
    }

    private LocalDate getValidHireDate(LocalDate birthday) {

        while (true) {
            System.out.print("Hire Date (YYYY-MM-DD): ");

            try {
                LocalDate hireDate = LocalDate.parse(scanner.nextLine());

                if (hireDate.isAfter(LocalDate.now())) {
                    System.out.println("Hire date cannot be in the future.");

                } else if (hireDate.isBefore(birthday)) {
                    System.out.println("Hire date cannot be earlier than birthday.");

                } else {
                    return hireDate;
                }

            } catch (DateTimeException e) {
                System.out.println("Invalid date. Please use YYYY-MM-DD.");
            }
        }
    }

    private String getValidContactNumber() {
        String contactNumber;

        while (true) {
            System.out.print("Contact Number (09XXXXXXXXX): ");
            contactNumber = scanner.nextLine().trim();

            if (contactNumber.isEmpty()) {
                System.out.println("Contact number cannot be empty.");
            }  else if (!contactNumber.matches("^09\\d{9}$")) {
                System.out.println("Invalid contact number. Please enter an 11-digit number starting with 09.");
            } else {
                return contactNumber;
            }
        }
    }

    private int getPositiveNumber(String message) {
        int number;

        while (true) {
            System.out.print(message);

            try {
                number = Integer.parseInt(scanner.nextLine());

                if (number > 0) {
                    return number;
                }

                System.out.println("Please enter a number greater than zero.");

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public void displayAllEmployees(List<Employee> employees) {

        System.out.println("\n------------------------------------------------------------------------------------------");
        System.out.printf("%52s%n", "EMPLOYEE LIST");
        System.out.println("------------------------------------------------------------------------------------------");

        if (employees == null || employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("%-5s | %-15s | %-15s | %-25s | %-15s%n",
                "ID", "First Name", "Last Name", "Email", "Status");
        System.out.println("------------------------------------------------------------------------------------------");

        for (Employee employee : employees) {
            System.out.printf("%-5d | %-15s | %-15s | %-25s | %-15s%n",
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getEmail(),
                    employee.getEmploymentStatus());
        }

        System.out.println("------------------------------------------------------------------------------------------");
    }
}
