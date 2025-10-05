package main;

import model.Employee;
import service.EmployeeService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;

public class UserDetailsView extends JFrame {

    public UserDetailsView(String empId) {
        setTitle("My Profile");
        setSize(900, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        EmployeeService service = new EmployeeService();
        Employee emp = service.getEmployeeDetails(Integer.parseInt(empId));

        if (emp == null) {
            JOptionPane.showMessageDialog(this, "Employee not found.");
            dispose();
            return;
        }

        // 🔹 Main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 245, 255));

        // 🔹 Heading
        JLabel heading = new JLabel("Welcome, " + emp.getName(), SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(20, 20, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(heading, BorderLayout.NORTH);

        // 🔹 Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 16);

        infoPanel.add(createLabel("Employee ID: " + emp.getId(), labelFont));
        infoPanel.add(createLabel("Role: " + emp.getRole(), labelFont));
        infoPanel.add(createLabel("Email: " + emp.getEmail(), labelFont));
        infoPanel.add(createLabel("Phone: " + emp.getPhoneNumber(), labelFont));
        infoPanel.add(createLabel("Department: " + emp.getDepartment(), labelFont));
        infoPanel.add(createLabel("Date of Joining: " + emp.getDateOfJoining(), labelFont));
        infoPanel.add(createLabel("Salary: ₹" + emp.getSalary(), labelFont));
        infoPanel.add(createLabel("Performance Score: " + emp.getPerformanceScore(), labelFont));
        infoPanel.add(createLabel("Performance Grade: " + emp.getPerformanceGrade(), labelFont));

        JScrollPane scrollPane = new JScrollPane(infoPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.WEST);

        // 🔹 Chart panel
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBackground(new Color(230, 245, 255));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(emp.getPerformanceScore(), "Score", emp.getName());

        JFreeChart chart = ChartFactory.createBarChart(
                "Performance Score",
                "Employee",
                "Score",
                dataset
        );

        ChartPanel chartSwingPanel = new ChartPanel(chart);
        chartSwingPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.add(chartSwingPanel, BorderLayout.CENTER);

        panel.add(chartPanel, BorderLayout.CENTER);

        // 🔹 Back button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(60, 120, 200));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(120, 40));

        backButton.addActionListener(e -> {
            new RoleSelectionLogin(); // ✅ returns to role selection
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(230, 245, 255));
        bottomPanel.add(backButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        exitButton.setBackground(new Color(200, 60, 60));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setPreferredSize(new Dimension(120, 40));

        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?",
                    "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // closes only this window
            }
        });

        bottomPanel.add(Box.createHorizontalStrut(20));
        bottomPanel.add(exitButton);

        add(panel);
        setVisible(true);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }

    public static void main(String[] args) {
        new UserDetailsView("101"); // test with valid ID

    }
}
