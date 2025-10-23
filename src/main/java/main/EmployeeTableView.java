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

        // 🔍 Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(230, 245, 255));

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        panel.add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        // 🔘 Buttons
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");
        JButton exitButton = new JButton("Exit");
        JButton exportButton = new JButton("Export to CSV");
        JButton dashboardButton = new JButton("View Dashboard"); // ✅ NEW BUTTON

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(230, 245, 255));

        for (JButton btn : new JButton[]{editButton, deleteButton, refreshButton, exitButton, exportButton, dashboardButton}) {
            btn.setFont(buttonFont);
            btn.setBackground(buttonColor);
            btn.setForeground(Color.WHITE);
            buttonPanel.add(btn);
        }

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // 🔹 Refresh button
        refreshButton.addActionListener(e -> {
            DefaultTableModel m = (DefaultTableModel) employeeTable.getModel();
            m.setRowCount(0);
            loadEmployeeData(m);
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
                    refreshButton.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Failed to delete employee.");
                }
            }
        });

        // 🔹 Edit button
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

        // 🔹 Exit button
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?",
                    "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        // 🔹 Search logic
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }

            private void filterTable() {
                String query = searchField.getText().toLowerCase();
                DefaultTableModel model = (DefaultTableModel) employeeTable.getModel();
                model.setRowCount(0);

                List<Employee> employees = employeeService.getAllEmployees();
                for (Employee emp : employees) {
                    String idStr = String.valueOf(emp.getId());
                    if (idStr.contains(query) ||
                            emp.getName().toLowerCase().contains(query) ||
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

        // 🔹 Export button
        exportButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Employee Data");
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                try (java.io.FileWriter fw = new java.io.FileWriter(fileToSave + ".csv")) {
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        fw.write(model.getColumnName(i) + (i < model.getColumnCount() - 1 ? "," : "\n"));
                    }
                    for (int i = 0; i < model.getRowCount(); i++) {
                        for (int j = 0; j < model.getColumnCount(); j++) {
                            fw.write(model.getValueAt(i, j).toString() + (j < model.getColumnCount() - 1 ? "," : "\n"));
                        }
                    }
                    JOptionPane.showMessageDialog(this, "✅ Data exported successfully!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "❌ Failed to export data.");
                }
            }
        });

        // ✅ Dashboard button
        dashboardButton.addActionListener(e -> {
            new PerformanceDashboard();
        });

//        JButton exportButton = new JButton("Export to Weka CSV");
        exportButton.addActionListener(e -> {
            EmployeeService service = new EmployeeService();
            boolean success = service.exportEmployeesToCSV("employees.csv");

            if (success) {
                JOptionPane.showMessageDialog(this, "CSV exported successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Export failed.");
            }
        });


        add(panel);
        setVisible(true);
    }

    private void loadEmployeeData(DefaultTableModel model) {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
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
