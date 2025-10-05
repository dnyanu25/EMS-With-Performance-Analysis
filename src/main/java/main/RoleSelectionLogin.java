package main;

import javax.swing.*;
import java.awt.*;

public class RoleSelectionLogin extends JFrame {

    public RoleSelectionLogin() {
        setTitle("Select Role");
        setSize(600, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🔹 Gradient background
        JPanel panel = new GradientPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // 🔹 Heading
        JLabel heading = new JLabel("Select Your Role", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 26));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setForeground(new Color(30, 30, 30));
        panel.add(heading);
        panel.add(Box.createVerticalStrut(30));

        // 🔹 Buttons
        JButton adminButton = new JButton("Admin Login");
        JButton userButton = new JButton("User Login");
        JButton exitButton = new JButton("Exit");

        for (JButton btn : new JButton[]{adminButton, userButton, exitButton}) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setBackground(btn == exitButton ? new Color(200, 60, 60) : new Color(60, 120, 200));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setPreferredSize(new Dimension(180, 40));
            panel.add(btn);
            panel.add(Box.createVerticalStrut(20));
        }

        // 🔹 Button actions
        adminButton.addActionListener(e -> {
            new AdminLoginForm();
            dispose();
        });

        userButton.addActionListener(e -> {
            new UserLoginForm();
            dispose();
        });

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?",
                    "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RoleSelectionLogin();
    }
}
