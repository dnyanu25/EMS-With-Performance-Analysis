package main;

import model.Employee;
import org.jfree.data.general.DefaultPieDataset;
import service.EmployeeService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;

public class PerformanceDashboard extends JFrame {

    public PerformanceDashboard() {
        setTitle("Performance Dashboard");
        setSize(900, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🔹 Main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 245, 255));

        // 🔹 Content panel with vertical layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(230, 245, 255));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 🔹 Heading
        JLabel heading = new JLabel("Employee Performance Dashboard", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(20, 20, 20));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(heading);
        contentPanel.add(Box.createVerticalStrut(10));

        // 🔹 Table columns
        String[] columns = {"ID", "Name", "Department", "Score", "Grade"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(22);

        // 🔹 Load top performers
        EmployeeService service = new EmployeeService();
        List<Employee> topPerformers = service.getTopPerformers(5);

        for (Employee emp : topPerformers) {
            model.addRow(new Object[]{
                    emp.getId(),
                    emp.getName(),
                    emp.getDepartment(),
                    emp.getPerformanceScore(),
                    emp.getPerformanceGrade()
            });
        }

        // 🔹 Scroll pane for table (fixed to 5 rows)
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(800, table.getRowHeight() * 5 + 30));
        tableScroll.setBorder(BorderFactory.createTitledBorder("Top Performers"));
        tableScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(tableScroll);
        contentPanel.add(Box.createVerticalStrut(10));

        // 🔹 Department averages
        Map<String, Double> averages = service.getAverageScoreByDepartment();
        StringBuilder avgText = new StringBuilder("<html><b>Average Scores by Department:</b><br>");
        for (Map.Entry<String, Double> entry : averages.entrySet()) {
            avgText.append("• ").append(entry.getKey()).append(": ")
                    .append(String.format("%.2f", entry.getValue())).append("<br>");
        }
        avgText.append("</html>");

        JLabel avgLabel = new JLabel(avgText.toString());
        avgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        avgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(avgLabel);
        contentPanel.add(Box.createVerticalStrut(10));

        // 🔹 Bar chart panel
        JPanel chartPanel = createBarChartPanel(averages);
        chartPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(chartPanel);

        // 🔹 Wrap content in scrollable wrapper
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapperPanel.setBackground(new Color(230, 245, 255));
        wrapperPanel.add(contentPanel);


        JScrollPane scrollPane = new JScrollPane(wrapperPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane, BorderLayout.CENTER);

        Map<String, Integer> gradeCounts = service.getGradeDistribution();
        JPanel pieChartPanel = createGradePieChartPanel(gradeCounts);
        pieChartPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(pieChartPanel);

       //predict grade
        JButton predictButton = new JButton("Predict Grade");
        predictButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(predictButton);
        predictButton.setFont(new Font("Arial", Font.BOLD, 14));
        predictButton.setPreferredSize(new Dimension(160, 40));

        JButton backButton = new JButton("Back to Employee Table");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(60, 120, 200));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(200, 40));

        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(backButton);


        backButton.addActionListener(e -> {
            new EmployeeTableView(); // open the table view
            dispose();               // close the dashboard
        });


        predictButton.addActionListener(e -> {
            try {
                // Step 1: Export data
                boolean success = service.exportToCSV("employees.csv");
                if (!success) {
                    JOptionPane.showMessageDialog(this, "Failed to export data.");
                    return;
                }

                // Step 2: Train model
                ml.WekaPredictor predictor = new ml.WekaPredictor();
                predictor.trainModel("employees.csv");

                // Step 3: Fetch departments
                List<String> deptList = service.getAllDepartments();
                if (deptList.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No departments found.");
                    return;
                }

                // Step 4: Create input panel
                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
                inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel deptLabel = new JLabel("Select Department:");
                deptLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                JComboBox<String> deptCombo = new JComboBox<>(deptList.toArray(new String[0]));
                deptCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

                JLabel scoreLabel = new JLabel("Enter Performance Score:");
                scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                JTextField scoreField = new JTextField(10);
                scoreField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

                inputPanel.add(deptLabel);
                inputPanel.add(Box.createVerticalStrut(5));
                inputPanel.add(deptCombo);
                inputPanel.add(Box.createVerticalStrut(10));
                inputPanel.add(scoreLabel);
                inputPanel.add(Box.createVerticalStrut(5));
                inputPanel.add(scoreField);

                int result = JOptionPane.showConfirmDialog(this, inputPanel, "Prediction Input", JOptionPane.OK_CANCEL_OPTION);
                if (result != JOptionPane.OK_OPTION) return;

                String dept = (String) deptCombo.getSelectedItem();
                double score = Double.parseDouble(scoreField.getText().trim());

                // Step 5: Predict
                String predictedGrade = predictor.predict(dept, score);

                // Step 6: Show result
                JPanel resultPanel = new JPanel();
                resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
                resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel title = new JLabel("Prediction Result");
                title.setFont(new Font("Arial", Font.BOLD, 16));
                title.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel resultLabel = new JLabel("Predicted Grade: " + predictedGrade);
                resultLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                resultPanel.add(title);
                resultPanel.add(Box.createVerticalStrut(10));
                resultPanel.add(resultLabel);

                JOptionPane.showMessageDialog(this, resultPanel, "Prediction", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Prediction failed.");
            }
        });

        add(panel);
        setVisible(true);
    }

    private JPanel createBarChartPanel(Map<String, Double> averages) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : averages.entrySet()) {
            dataset.addValue(entry.getValue(), "Average Score", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Average Performance Score by Department",
                "Department",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 300));
        return chartPanel;
    }

    private JPanel createGradePieChartPanel(Map<String, Integer> gradeCounts) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (Map.Entry<String, Integer> entry : gradeCounts.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Performance Grade Distribution",
                dataset,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 300));
        return chartPanel;
    }

    public static void main(String[] args) {
        new PerformanceDashboard();
    }
}
