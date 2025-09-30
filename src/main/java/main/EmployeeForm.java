package main;

import model.Employee;
import service.EmployeeService;
import javax.swing.*;
import java.awt.*;

public class EmployeeForm extends JFrame {

    private JTextField nameField;
    private final JTextField roleField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField salaryField;
    private JTextField dateField;
    private JTextField scoreField;
    private JTextField managerField;
    private JComboBox<String> departmentBox, gradeBox;
    private JCheckBox activeBox;
    private JButton registerButton;
    private EmployeeService employeeService = new EmployeeService();

    // 🔹 Constructor for NEW employee registration
    public EmployeeForm() {
        setTitle("Employee Registration");
        setSize(550, 550);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(230, 245, 255));

        JLabel heading = new JLabel("Employee Registration Form", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(20, 20, 20));
        panel.add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(12, 2, 10, 10));
        formPanel.setBackground(new Color(245, 250, 255));

        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Fields
        formPanel.add(createLabel("Name:"));
        nameField = new JTextField(); nameField.setFont(fieldFont);
        formPanel.add(nameField);

        formPanel.add(createLabel("Role:"));
        roleField = new JTextField(); roleField.setFont(fieldFont);
        formPanel.add(roleField);

        formPanel.add(createLabel("Email:"));
        emailField = new JTextField(); emailField.setFont(fieldFont);
        formPanel.add(emailField);

        formPanel.add(createLabel("Phone:"));
        phoneField = new JTextField(); phoneField.setFont(fieldFont);
        formPanel.add(phoneField);

        formPanel.add(createLabel("Department:"));
        departmentBox = new JComboBox<>(new String[]{
                "-- Select Department --", "HR", "Finance", "IT", "Marketing"
        });
        departmentBox.setFont(fieldFont);
        formPanel.add(departmentBox);

        formPanel.add(createLabel("Date of Joining (yyyy-mm-dd):"));
        dateField = new JTextField(); dateField.setFont(fieldFont);
        formPanel.add(dateField);

        formPanel.add(createLabel("Salary:"));
        salaryField = new JTextField(); salaryField.setFont(fieldFont);
        formPanel.add(salaryField);

        formPanel.add(createLabel("Performance Score:"));
        scoreField = new JTextField(); scoreField.setFont(fieldFont);
        formPanel.add(scoreField);

        formPanel.add(createLabel("Performance Grade:"));
        gradeBox = new JComboBox<>(new String[]{"A", "B", "C", "D", "E"});
        gradeBox.setFont(fieldFont);
        formPanel.add(gradeBox);

        formPanel.add(createLabel("Manager Name:"));
        managerField = new JTextField(); managerField.setFont(fieldFont);
        formPanel.add(managerField);

        formPanel.add(createLabel("Is Active:"));
        activeBox = new JCheckBox("Active");
        activeBox.setFont(fieldFont);
        activeBox.setSelected(true);
        formPanel.add(activeBox);

        // Register/Update button
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        registerButton.setBackground(new Color(60, 120, 200));
        registerButton.setForeground(Color.WHITE);
        formPanel.add(registerButton);
        formPanel.add(new JLabel()); // spacer

        panel.add(formPanel, BorderLayout.CENTER);
        add(panel);
        setVisible(true);

        // Default action: Register new employee
        registerButton.addActionListener(e -> registerEmployee());
    }
    // 🔹 Constructor for EDITING an existing employee
    public EmployeeForm(Employee emp) {
        this(); // build UI first

        // Pre-fill fields
        nameField.setText(emp.getName());
        roleField.setText(emp.getRole());
        emailField.setText(emp.getEmail());
        phoneField.setText(emp.getPhoneNumber());
        departmentBox.setSelectedItem(emp.getDepartment());
        dateField.setText(emp.getDateOfJoining().toString());
        salaryField.setText(String.valueOf(emp.getSalary()));
        scoreField.setText(String.valueOf(emp.getPerformanceScore()));
        gradeBox.setSelectedItem(emp.getPerformanceGrade());
        managerField.setText(emp.getManagerName());
        activeBox.setSelected(emp.isActive());

        // Change button to "Update"
        registerButton.setText("Update");

        // Remove old listeners and add update logic
        for (var al : registerButton.getActionListeners()) {
            registerButton.removeActionListener(al);
        }

        registerButton.addActionListener(e -> updateEmployee(emp));
    }

    // 🔹 Register new employee
    private void registerEmployee() {
        try {
            if (!validateFields()) return;

            Employee emp = new Employee();
            fillEmployeeFromForm(emp);

            boolean success = employeeService.registerEmployee(emp);
            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Employee registered successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to register employee.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠️ Error registering employee.");
        }
    }

    // 🔹 Update existing employee
    private void updateEmployee(Employee emp) {
        try {
            if (!validateFields()) return;

            fillEmployeeFromForm(emp);

            boolean success = employeeService.updateEmployeeInfo(emp);
            if (success) {
                JOptionPane.showMessageDialog(this, "✅ Employee updated successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to update employee.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠️ Error updating employee.");
        }
    }

    // 🔹 Helper: Validate fields
    private boolean validateFields() {
        if (nameField.getText().isEmpty() || roleField.getText().isEmpty() ||
                emailField.getText().isEmpty() || phoneField.getText().isEmpty() ||
                departmentBox.getSelectedIndex() == 0 || dateField.getText().isEmpty() ||
                salaryField.getText().isEmpty() || scoreField.getText().isEmpty() ||
                managerField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields.");
            return false;
        }
        return true;
    }
    // 🔹 Helper: Fill Employee object from form
    private void fillEmployeeFromForm(Employee emp) {
        emp.setName(nameField.getText());
        emp.setRole(roleField.getText());
        emp.setEmail(emailField.getText());
        emp.setPhoneNumber(phoneField.getText());
        emp.setDepartment((String) departmentBox.getSelectedItem());
        emp.setDateOfJoining(java.sql.Date.valueOf(dateField.getText()));
        emp.setSalary(Double.parseDouble(salaryField.getText()));
        emp.setPerformanceScore(Double.parseDouble(scoreField.getText()));
        emp.setPerformanceGrade((String) gradeBox.getSelectedItem());
        emp.setManagerName(managerField.getText());
        emp.setIsActive(activeBox.isSelected());
    }
    // 🔹 Helper: Create styled label
    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(new Color(40, 40, 40));
        lbl.setHorizontalAlignment(SwingConstants.RIGHT);
        return lbl;
    }

    public static void main(String[] args) {
        new EmployeeForm(); // default: register mode
    }
}
