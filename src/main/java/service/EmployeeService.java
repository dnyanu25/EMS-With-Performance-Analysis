package service;

import dao.EmployeeDao;
import model.Employee;
import dao.DatabaseManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;
import java.util.Map;
import java.util.LinkedHashMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    private EmployeeDao employeeDao = new EmployeeDao();

    public boolean registerEmployee(Employee emp) {
        try {
            // your insert code
                    return employeeDao.addEmployee(emp);

        } catch (Exception e) {
            e.printStackTrace(); // 👈 shows the real error in console
            return false;
        }
    }

    public Employee getEmployeeDetails(int id) {
        return employeeDao.getEmployeeById(id);
    }
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    public boolean updateEmployeeInfo(Employee emp) {
        return employeeDao.updateEmployee(emp);
    }

    public boolean removeEmployee(int id) {
        return employeeDao.deleteEmployee(id);
    }

    public List<Employee> getTopPerformers(int limit) {
        List<Employee> topList = new ArrayList<>();
        try (Connection conn = dao.DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, name, department, performanceScore, performanceGrade FROM employees ORDER BY performanceScore DESC LIMIT ?")) {

            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setDepartment(rs.getString("department"));
                emp.setPerformanceScore(rs.getDouble("performanceScore"));
                emp.setPerformanceGrade(rs.getString("performanceGrade"));
                topList.add(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topList;
    }

    public Map<String, Double> getAverageScoreByDepartment() {
        Map<String, Double> averages = new LinkedHashMap<>();
        try (Connection conn = dao.DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT department, AVG(performanceScore) AS avg_score FROM employees GROUP BY department")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String dept = rs.getString("department");
                double avg = rs.getDouble("avg_score");
                averages.put(dept, avg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return averages;
    }

    public Map<String, Integer> getGradeDistribution() {
        Map<String, Integer> gradeCounts = new LinkedHashMap<>();
        try (Connection conn = dao.DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT performanceGrade, COUNT(*) AS count FROM employees GROUP BY performanceGrade")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String grade = rs.getString("performanceGrade");
                int count = rs.getInt("count");
                gradeCounts.put(grade, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gradeCounts;
    }

    public boolean exportToCSV(String filePath) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT department, performanceScore, performanceGrade FROM employees");
             ResultSet rs = stmt.executeQuery();
             java.io.PrintWriter writer = new java.io.PrintWriter(filePath)) {

            writer.println("department,performanceScore,performanceGrade");

            while (rs.next()) {
                String dept = rs.getString("department");
                double score = rs.getDouble("performanceScore");
                String grade = rs.getString("performanceGrade");
                writer.println(dept + "," + score + "," + grade);
            }

            writer.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getAllDepartments() {
        List<String> departments = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT DISTINCT department FROM employees");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                departments.add(rs.getString("department"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return departments;
    }

    public boolean validateUserLogin(String empId, String dateOfJoining) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM employees WHERE id = ? AND dateOfJoining = ?")) {

            stmt.setInt(1, Integer.parseInt(empId)); // assuming ID is numeric
            stmt.setString(2, dateOfJoining);        // format: "YYYY-MM-DD"

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean exportEmployeesToCSV(String filePath) {
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT department, performanceScore, performanceGrade FROM employees");
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("department,performanceScore,performanceGrade\n");

            while (rs.next()) {
                String dept = rs.getString("department");
                double score = rs.getDouble("performanceScore");
                String grade = rs.getString("performanceGrade");

                writer.write(dept + "," + score + "," + grade + "\n");
            }

            writer.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}