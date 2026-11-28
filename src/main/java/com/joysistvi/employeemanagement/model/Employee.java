package com.joysistvi.employeemanagement.model;

import java.time.LocalDate;

public class Employee {

    private int employeeId;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthday;
    private String contactNumber;
    private String email;
    private LocalDate hireDate;
    private int departmentId;
    private int positionId;
    private String departmentName;
    private String positionName;
    private String address;
    private String employmentStatus;
    private int leaveCredits;
    private boolean archived;

    public Employee(int employeeId, String firstName, String middleName, String lastName, LocalDate birthday, String contactNumber, String email, LocalDate hireDate, int departmentId, int positionId, String address, String employmentStatus, int leaveCredits, boolean archived) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.contactNumber = contactNumber;
        this.email = email;
        this.hireDate = hireDate;
        this.departmentId = departmentId;
        this.positionId = positionId;
        this.address = address;
        this.employmentStatus = employmentStatus;
        this.leaveCredits = leaveCredits;
        this.archived = archived;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public int getLeaveCredits() {
        return leaveCredits;
    }

    public void setLeaveCredits(int leaveCredits) {
        this.leaveCredits = leaveCredits;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getId() {
        return employeeId;
    }

    public void setId(int id) {
        this.employeeId = id;
    }

    public String getFullName() {
        String middle = (middleName == null || middleName.isBlank()) ? "" : middleName + " ";
        return firstName + " " + middle + lastName;
    }
}