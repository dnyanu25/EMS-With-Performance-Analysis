package main;

import javax.swing.*;
import java.awt.*;

public class WelcomeSplashScreen extends JFrame {

    public WelcomeSplashScreen() {
        setTitle("Welcome");

        setSize(700, 400);
        setUndecorated(true);
        setOpacity(0f);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 🔹 Gradient background
        JPanel panel = new GradientPanel();
        panel.setLayout(new BorderLayout());

        // 🔹 Welcome message
        JLabel welcomeLabel = new JLabel("Welcome to Employee Portal", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(40, 40, 40));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));

        JLabel subLabel = new JLabel("Secure • Smart • Personalized", SwingConstants.CENTER);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subLabel.setForeground(new Color(80, 80, 80));
        subLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(subLabel, BorderLayout.CENTER);

        add(panel);
        fadeInWindow();

        // 🔹 Auto-redirect after delay
        Timer timer = new Timer(2500, e -> {
            new RoleSelectionLogin();
            dispose();
        });
        timer.setRepeats(false);
        timer.start();

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
        new WelcomeSplashScreen();
    }
}
