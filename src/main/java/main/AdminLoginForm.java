package main;

import javax.swing.*;
import java.awt.*;

public class AdminLoginForm extends JFrame {

    public AdminLoginForm() {
        setTitle("Admin Login");
        setSize(500, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center window

        // Main panel with BorderLayout
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(230, 240, 250)); // light background

        // Heading label
        JLabel heading = new JLabel("Admin Login", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(20, 20, 20)); // dark bold color
        panel.add(heading, BorderLayout.NORTH);

        // Sub-panel for form fields
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setOpaque(false); // keep background consistent

        formPanel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(60, 120, 200)); // dark blue
        loginButton.setForeground(Color.WHITE);
        formPanel.add(loginButton);
        formPanel.add(new JLabel()); // spacer

        panel.add(formPanel, BorderLayout.CENTER);

        add(panel);

        // Button action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.");
                return;
            }

            if (username.equals("admin") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Welcome, Admin!");
                // Next step: open employee table view
                new EmployeeTableView();
                dispose(); // close login window
            } else {
                JOptionPane.showMessageDialog(this, "Access denied. Admin only.");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new AdminLoginForm();
    }
}
