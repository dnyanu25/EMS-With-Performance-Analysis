package dao;
import model.Department;
import model.Employee;
import java.sql.*;
import java.util.*;
public class DepartmentDao {

    public boolean addDepartment(Department dept) {
        try (Connection conn = DatabaseManager.getConnection()) {

            // Step 1: Create table if not exists
            String createTableSQL = "CREATE TABLE IF NOT EXISTS departments (" +
                    "id INT PRIMARY KEY," +
                    "name VARCHAR(100)," +
                    "manager VARCHAR(100)," +
                    "employeeCount INT" +
                    ")";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
            }

            // Step 2: Insert department data
            String insertSQL = "INSERT INTO departments (id, name, manager, employeeCount) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                pstmt.setInt(1, dept.getId());
                pstmt.setString(2, dept.getName());
                pstmt.setString(3, dept.getManager());
                pstmt.setInt(4, dept.getEmployeeCount());

                int rowsInserted = pstmt.executeUpdate();
                return rowsInserted > 0;
            }

        } catch (Exception e) {
            System.out.println("❌ Error inserting department: " + e.getMessage());
            return false;
        }
    }

    public Department getDepartmentById(int id) {
        String sql = "SELECT * FROM departments WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Department dept = new Department();
                dept.setId(rs.getInt("id"));
                dept.setName(rs.getString("name"));
                dept.setManager(rs.getString("manager"));
                dept.setEmployeeCount(rs.getInt("employeeCount"));
                return dept;
            }

        } catch (Exception e) {
            System.out.println("❌ Error fetching department: " + e.getMessage());
        }

        return null;
    }

    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Department dept = new Department();
                dept.setId(rs.getInt("id"));
                dept.setName(rs.getString("name"));
                dept.setManager(rs.getString("manager"));
                dept.setEmployeeCount(rs.getInt("employeeCount"));
                departments.add(dept);
            }

        } catch (Exception e) {
            System.out.println("❌ Error fetching departments: " + e.getMessage());
        }

        return departments;
    }

    public boolean updateDepartment(Department dept) {
        String sql = "UPDATE departments SET name=?, manager=?, employeeCount=? WHERE id=?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dept.getName());
            stmt.setString(2, dept.getManager());
            stmt.setInt(3, dept.getEmployeeCount());
            stmt.setInt(4, dept.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            System.out.println("❌ Error updating department: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDepartment(int id) {
        String sql = "DELETE FROM departments WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (Exception e) {
            System.out.println("❌ Error deleting department: " + e.getMessage());
            return false;
        }
    }

}
