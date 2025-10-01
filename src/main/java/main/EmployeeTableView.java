package main;

import model.Employee;
import service.EmployeeService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeTableView extends JFrame {

    private EmployeeService employeeService = new EmployeeService();
    private JTable employeeTable;

    public EmployeeTableView() {
        setTitle("All Employees");
        setSize(800, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // center window

        // Main panel
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(230, 245, 255));

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Color buttonColor = new Color(60, 120, 200);


        // Heading
        JLabel heading = new JLabel("All Employees", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(20, 20, 20));
        panel.add(heading, BorderLayout.NORTH);

        // Table model
        String[] columns = {"ID", "Name", "Role", "Email", "Phone", "Department",
                "Date of Joining", "Salary", "Score", "Grade", "Manager", "Active"};

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        employeeTable = new JTable(model);
        employeeTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        employeeTable.setRowHeight(22);

        // Load data
        loadEmployeeData(model);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 245, 255));


        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");
        JButton exitButton = new JButton("Exit");

        // 🔍 Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(230, 245, 255));

        JLabel searchLabel = new JLabel("SearchByName:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        panel.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);



        for (JButton btn : new JButton[]{editButton, deleteButton, refreshButton,exitButton}) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonColor);
            btn.setForeground(Color.WHITE);
            buttonPanel.add(btn);
        }

        panel.add(buttonPanel, BorderLayout.SOUTH);

// 🔹 Refresh button
        refreshButton.addActionListener(e -> {
            DefaultTableModel m = (DefaultTableModel) employeeTable.getModel();
            m.setRowCount(0); // clear table
            loadEmployeeData(m); // reload data
        });

// 🔹 Delete button
        deleteButton.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Please select an employee to delete.");
                return;
            }
            int id = (int) employeeTable.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete employee ID " + id + "?",
                    "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = employeeService.removeEmployee(id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "✅ Employee deleted.");
                    refreshButton.doClick(); // reload table
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Failed to delete employee.");
                }
            }
        });

// 🔹 Edit button (basic placeholder for now)
        editButton.addActionListener(e -> {
            int row = employeeTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Please select an employee to edit.");
                return;
            }
            int id = (int) employeeTable.getValueAt(row, 0);
            Employee emp = employeeService.getEmployeeDetails(id);
            if (emp != null) {
                JOptionPane.showMessageDialog(this, "Editing employee: " + emp.getName());
                // 👉 Later: open EmployeeForm pre-filled with emp’s data

            }
        });


//   exit button
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?",
                    "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // closes only this window
                // System.exit(0); // 👉 use this if you want to close the entire app
            }
        });

        //search

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }

            private void filterTable() {
                String query = searchField.getText().toLowerCase();
                DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
                model.setRowCount(0); // clear table

                List<Employee> employees = employeeService.getAllEmployees();
                for (Employee emp : employees) {
                    if (emp.getName().toLowerCase().contains(query) ||
                            emp.getDepartment().toLowerCase().contains(query)) {
                        model.addRow(new Object[]{
                                emp.getId(),
                                emp.getName(),
                                emp.getRole(),
                                emp.getEmail(),
                                emp.getPhoneNumber(),
                                emp.getDepartment(),
                                emp.getDateOfJoining(),
                                emp.getSalary(),
                                emp.getPerformanceScore(),
                                emp.getPerformanceGrade(),
                                emp.getManagerName(),
                                emp.isActive() ? "Yes" : "No"
                        });
                    }
                }
            }
        });


        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
    private void loadEmployeeData(DefaultTableModel model) {
        try {
            List<Employee> employees = employeeService.getAllEmployees(); // you’ll need this in your DAO
            for (Employee emp : employees) {
                model.addRow(new Object[]{
                        emp.getId(),
                        emp.getName(),
                        emp.getRole(),
                        emp.getEmail(),
                        emp.getPhoneNumber(),
                        emp.getDepartment(),
                        emp.getDateOfJoining(),
                        emp.getSalary(),
                        emp.getPerformanceScore(),
                        emp.getPerformanceGrade(),
                        emp.getManagerName(),
                        emp.isActive() ? "Yes" : "No"
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "⚠️ Failed to load employee data.");
        }
    }

    public static void main(String[] args) {
        new EmployeeTableView();
    }
}
