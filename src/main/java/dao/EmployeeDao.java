package dao;

import model.Employee;
import java.sql.*;
import java.util.*;

public class EmployeeDao {

    public boolean addEmployee(Employee emp) {
        try (Connection conn = DatabaseManager.getConnection()) {

            // Create table if not exists (with AUTO_INCREMENT)
            String createTableSQL = "CREATE TABLE IF NOT EXISTS employees (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(100)," +
                    "role VARCHAR(50)," +
                    "email VARCHAR(100)," +
                    "phoneNumber VARCHAR(20)," +
                    "department VARCHAR(50)," +
                    "dateOfJoining DATE," +
                    "salary DOUBLE," +
                    "performanceScore DOUBLE," +
                    "performanceGrade VARCHAR(10)," +
                    "managerName VARCHAR(100)," +
                    "isActive BOOLEAN" +
                    ")";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
            }

            // Insert (without id, since AUTO_INCREMENT handles it)
            String insertSQL = "INSERT INTO employees (name, role, email, phoneNumber, department, dateOfJoining, salary, performanceScore, performanceGrade, managerName, isActive) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setString(1, emp.getName());
                pstmt.setString(2, emp.getRole());
                pstmt.setString(3, emp.getEmail());
                pstmt.setString(4, emp.getPhoneNumber());
                pstmt.setString(5, emp.getDepartment());
                pstmt.setDate(6, new java.sql.Date(emp.getDateOfJoining().getTime()));
                pstmt.setDouble(7, emp.getSalary());
                pstmt.setDouble(8, emp.getPerformanceScore());
                pstmt.setString(9, emp.getPerformanceGrade());
                pstmt.setString(10, emp.getManagerName());
                pstmt.setBoolean(11, emp.isActive());

                int rowsInserted = pstmt.executeUpdate();
                return rowsInserted > 0;
            }

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            return false;
        }
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setRole(rs.getString("role"));
                emp.setEmail(rs.getString("email"));
                emp.setPhoneNumber(rs.getString("phoneNumber"));
                emp.setDepartment(rs.getString("department"));
                emp.setDateOfJoining(rs.getDate("dateOfJoining"));
                emp.setSalary(rs.getDouble("salary"));
                emp.setPerformanceScore(rs.getDouble("performanceScore"));
                emp.setPerformanceGrade(rs.getString("performanceGrade"));
                emp.setManagerName(rs.getString("managerName"));
                emp.setIsActive(rs.getBoolean("isActive"));
                return emp;
            }

        } catch (Exception e) {
            System.out.println("❌ Error fetching employee: " + e.getMessage());
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setName(rs.getString("name"));
                emp.setRole(rs.getString("role"));
                emp.setEmail(rs.getString("email"));
                emp.setPhoneNumber(rs.getString("phoneNumber"));
                emp.setDepartment(rs.getString("department"));
                emp.setDateOfJoining(rs.getDate("dateOfJoining"));
                emp.setSalary(rs.getDouble("salary"));
                emp.setPerformanceScore(rs.getDouble("performanceScore"));
                emp.setPerformanceGrade(rs.getString("performanceGrade"));
                emp.setManagerName(rs.getString("managerName"));
                emp.setIsActive(rs.getBoolean("isActive"));
                employees.add(emp);
            }

        } catch (Exception e) {
            System.out.println("❌ Error fetching employees: " + e.getMessage());
        }

        return employees;
    }

    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET name=?, role=?, email=?, phoneNumber=?, department=?, dateOfJoining=?, salary=?, performanceScore=?, performanceGrade=?, managerName=?, isActive=? WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getRole());
            stmt.setString(3, emp.getEmail());
            stmt.setString(4, emp.getPhoneNumber());
            stmt.setString(5, emp.getDepartment());
            stmt.setDate(6, new java.sql.Date(emp.getDateOfJoining().getTime()));
            stmt.setDouble(7, emp.getSalary());
            stmt.setDouble(8, emp.getPerformanceScore());
            stmt.setString(9, emp.getPerformanceGrade());
            stmt.setString(10, emp.getManagerName());
            stmt.setBoolean(11, emp.isActive());
            stmt.setInt(12, emp.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            System.out.println("❌ Error updating employee: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (Exception e) {
            System.out.println("❌ Error deleting employee: " + e.getMessage());
            return false;
        }
    }

}
