package main;

import service.EmployeeService;

import javax.swing.*;
import java.awt.*;

public class UserLoginForm extends JFrame {

    private JTextField idField;

    public UserLoginForm() {
        setTitle("User Login");
        setSize(900, 500);
        setUndecorated(true);
        setOpacity(0f);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 🔹 Gradient background
        JPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());

        // 🔹 Heading
        JLabel heading = new JLabel("User Login", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        heading.setForeground(new Color(30, 30, 30));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(heading, BorderLayout.NORTH);

        // 🔹 Form panel
        JPanel formWrapper = new JPanel();
        formWrapper.setLayout(new BoxLayout(formWrapper, BoxLayout.Y_AXIS));
        formWrapper.setBackground(Color.WHITE);
        formWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
        ));
        formWrapper.setMaximumSize(new Dimension(400, 300));
        formWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel idLabel = new JLabel("Employee ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        idField = new JTextField();
        idField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel passLabel = new JLabel("Date of Joining (YYYY-MM-DD):");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginButton.setBackground(new Color(60, 120, 200));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setPreferredSize(new Dimension(120, 40));

        // 🔹 Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(100, 100, 100));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.addActionListener(e -> {
            new RoleSelectionLogin();
            dispose();
        });



        formWrapper.add(idLabel);
        formWrapper.add(Box.createVerticalStrut(5));
        formWrapper.add(idField);
        formWrapper.add(Box.createVerticalStrut(15));
        formWrapper.add(passLabel);
        formWrapper.add(Box.createVerticalStrut(5));
        formWrapper.add(passwordField);
        formWrapper.add(Box.createVerticalStrut(20));
        formWrapper.add(loginButton);
        formWrapper.add(Box.createVerticalStrut(10));
        formWrapper.add(backButton);
        formWrapper.add(Box.createVerticalStrut(10));

        // 🔹 Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(230, 240, 250));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(formWrapper);
        centerPanel.add(Box.createVerticalGlue());

        panel.add(centerPanel, BorderLayout.CENTER);
        add(panel);

        // 🔹 Login action
        loginButton.addActionListener(e -> {
            String empId = idField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (empId.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Employee ID and Date of Joining.");
                return;
            }

            EmployeeService service = new EmployeeService();
            boolean valid = service.validateUserLogin(empId, password);

            if (valid) {
                JOptionPane.showMessageDialog(this, "Welcome, Employee " + empId + "!");
                new UserDetailsView(empId);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Try again.");
            }
        });

        fadeInWindow();
        setVisible(true);
    }

    private void fadeInWindow() {
        for (float opacity = 0.0f; opacity <= 1.0f; opacity += 0.05f) {
            try {
                Thread.sleep(30);
                setOpacity(opacity);
            } catch (InterruptedException ignored) {}
        }
    }

    public static void main(String[] args) {
        new UserLoginForm();
    }
}
