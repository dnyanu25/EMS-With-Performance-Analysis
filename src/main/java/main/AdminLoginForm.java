package main;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AdminLoginForm extends JFrame {

    public AdminLoginForm() {
        setTitle("Admin Login");
        setSize(900, 500);
        setUndecorated(true); // required for opacity animation
        setOpacity(0f);       // start invisible
        setLocationRelativeTo(null); // center window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 🔹 Gradient background panel
        JPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());

        // 🔹 Heading
        JLabel heading = new JLabel("Admin Login", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        heading.setForeground(new Color(30, 30, 30));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(heading, BorderLayout.NORTH);

        // 🔹 Logo
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/logo.png")));
        Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaled));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 🔹 Card-style form panel
        JPanel formWrapper = new JPanel();
        formWrapper.setLayout(new BoxLayout(formWrapper, BoxLayout.Y_AXIS));
        formWrapper.setBackground(Color.WHITE);
        formWrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 30, 30, 30),
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1)
        ));
        formWrapper.setMaximumSize(new Dimension(400, 250));
        formWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel passLabel = new JLabel("Password:");
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

        formWrapper.add(userLabel);
        formWrapper.add(Box.createVerticalStrut(5));
        formWrapper.add(usernameField);
        formWrapper.add(Box.createVerticalStrut(15));
        formWrapper.add(passLabel);
        formWrapper.add(Box.createVerticalStrut(5));
        formWrapper.add(passwordField);
        formWrapper.add(Box.createVerticalStrut(20));
        formWrapper.add(loginButton);

        // 🔹 Center panel with logo and form
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(230, 240, 250));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(logoLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(formWrapper);
        centerPanel.add(Box.createVerticalGlue());

        panel.add(centerPanel, BorderLayout.CENTER);
        add(panel);

        // 🔹 Button action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both username and password.");
                return;
            }

            if (username.equals("admin") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Welcome, Admin!");
                new EmployeeTableView();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Access denied. Admin only.");
            }
        });

        fadeInWindow(); // animate window
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
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }
        new AdminLoginForm();
    }
}
