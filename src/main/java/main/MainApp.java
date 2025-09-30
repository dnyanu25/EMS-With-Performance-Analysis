package main;

public class MainApp {
}
/*
package main;

import dao.DatabaseManager;
import java.sql.Connection;

public class MainApp {
    public static void main(String[] args) {
        try (Connection conn = DatabaseManager.getConnection()) {
            System.out.println("✅ Connection successful!");
        } catch (Exception e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
        }
    }
}*/
