package model;

import java.util.Date;

public class Employee {


    private int id;
    private String name;
    private String role;
    private String email;
    private String phoneNumber;
    private String department;
    private Date dateOfJoining;
    private double salary;
    private double performanceScore;
    private String performanceGrade;
    private String managerName;
    private boolean isActive;


    public Employee() {}

    public Employee(int id, String name, String role, String email, String phoneNumber, String department, Date dateOfJoining, double salary, double performanceScore, String performanceGrade, String managerName, boolean isActive) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.department = department;
        this.dateOfJoining = dateOfJoining;
        this.salary = salary;
        this.performanceScore = performanceScore;
        this.performanceGrade = performanceGrade;
        this.managerName = managerName;
        this.isActive = isActive;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setPerformanceScore(double performanceScore) {
        this.performanceScore = performanceScore;
    }

    public void setPerformanceGrade(String performanceGrade) {
        this.performanceGrade = performanceGrade;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public double getSalary() {
        return salary;
    }

    public double getPerformanceScore() {
        return performanceScore;
    }

    public String getPerformanceGrade() {
        return performanceGrade;
    }

    public String getManagerName() {
        return managerName;
    }

    public boolean isActive() {
        return isActive;
    }



    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", department='" + department + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                ", salary=" + salary +
                ", performanceScore=" + performanceScore +
                ", performanceGrade='" + performanceGrade + '\'' +
                ", managerName='" + managerName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
