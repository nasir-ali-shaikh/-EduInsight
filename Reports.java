package eduinsight;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Reports extends JFrame {

    // ================= COLORS =================

    private final Color BG = new Color(245, 247, 252);
    private final Color WHITE = Color.WHITE;
    private final Color PRIMARY = new Color(99, 91, 255);
    private final Color TEXT = new Color(25, 31, 45);
    private final Color MUTED = new Color(116, 124, 142);
    private final Color GREEN = new Color(34, 197, 140);
    private final Color RED = new Color(239, 68, 68);

    // ================= LABELS =================

    private JLabel datasetLabel;
    private JLabel statusLabel;
    private JTextArea reportArea;
    private JButton exportButton;

    // ================= DATA =================

    private String generatedReportText;

    // ================= CONSTRUCTOR =================

    public Reports() {

        setTitle("EduInsight | Reports");

        setSize(1050, 780);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        main.add(createTopBar(), BorderLayout.NORTH);
        main.add(createContent(), BorderLayout.CENTER);

        setContentPane(main);

        loadReport();
    }

    // ================= TOP BAR =================

    private JPanel createTopBar() {

        JPanel bar = new JPanel(new BorderLayout());

        bar.setBackground(WHITE);
        bar.setBorder(new EmptyBorder(18, 28, 18, 28));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Reports");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Summary report generated from your dataset");
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));

        left.add(title);
        left.add(Box.createVerticalStrut(4));
        left.add(subtitle);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        right.setOpaque(false);

        exportButton = new JButton("Export Report (.txt)");
        stylePrimaryButton(exportButton);
        exportButton.setEnabled(false);
        exportButton.addActionListener(e -> exportReport());

        JButton backButton = new JButton("← Back to Dashboard");
        styleOutlineButton(backButton);

        backButton.addActionListener(e -> {
            dispose();
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        });

        right.add(exportButton);
        right.add(backButton);

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);

        return bar;
    }

    // ================= CONTENT =================

    private JPanel createContent() {

        JPanel content = new JPanel(new BorderLayout());

        content.setBackground(BG);
        content.setBorder(new EmptyBorder(25, 30, 25, 30));

        content.add(createDatasetCard(), BorderLayout.NORTH);
        content.add(createReportCard(), BorderLayout.CENTER);
        content.add(createStatusCard(), BorderLayout.SOUTH);

        return content;
    }

    // ================= DATASET CARD =================

    private JPanel createDatasetCard() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(225, 228, 238)),
                        new EmptyBorder(18, 22, 18, 22)
                )
        );

        JLabel title = new JLabel("CURRENT DATASET");
        title.setForeground(MUTED);
        title.setFont(new Font("SansSerif", Font.BOLD, 10));

        datasetLabel = new JLabel("No dataset selected");
        datasetLabel.setForeground(TEXT);
        datasetLabel.setFont(new Font("SansSerif", Font.BOLD, 15));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        left.add(title);
        left.add(Box.createVerticalStrut(6));
        left.add(datasetLabel);

        card.add(left, BorderLayout.WEST);

        wrapper.add(card, BorderLayout.CENTER);

        return wrapper;
    }

    // ================= REPORT CARD =================

    private JPanel createReportCard() {

        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        reportArea = new JTextArea("Generating report...");
        reportArea.setEditable(false);
        reportArea.setLineWrap(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        reportArea.setForeground(TEXT);
        reportArea.setBackground(WHITE);
        reportArea.setBorder(new EmptyBorder(4, 4, 4, 4));

        JScrollPane scroll = new JScrollPane(reportArea);
        scroll.setBorder(null);

        card.add(scroll, BorderLayout.CENTER);

        return card;
    }

    // ================= STATUS CARD =================

    private JPanel createStatusCard() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(20, 0, 0, 0));

        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(WHITE);
        card.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("STATUS");
        title.setForeground(MUTED);
        title.setFont(new Font("SansSerif", Font.BOLD, 10));

        statusLabel = new JLabel("Waiting for dataset...");
        statusLabel.setForeground(MUTED);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        left.add(title);
        left.add(Box.createVerticalStrut(6));
        left.add(statusLabel);

        card.add(left, BorderLayout.WEST);

        wrapper.add(card, BorderLayout.CENTER);

        return wrapper;
    }

    // =====================================================
    // LOAD REPORT
    // =====================================================

    private void loadReport() {

        if (!DatasetManager.hasDataset()) {

            datasetLabel.setText("No dataset selected");

            statusLabel.setText("Please upload a dataset first.");
            statusLabel.setForeground(MUTED);

            reportArea.setText("No dataset selected. Upload a dataset to generate a report.");

            return;
        }

        String path = DatasetManager.getDatasetPath();

        datasetLabel.setText(new File(path).getName());

        statusLabel.setText("Generating report...");
        statusLabel.setForeground(PRIMARY);

        SwingWorker<String, Void> worker = new SwingWorker<>() {

            @Override
            protected String doInBackground() throws Exception {
                return PythonEngine.run(path, "analytics");
            }

            @Override
            protected void done() {

                try {

                    String json = get();
                    buildReport(json);

                } catch (Exception e) {

                    statusLabel.setText("Failed to generate report");
                    statusLabel.setForeground(RED);

                    Throwable cause = e.getCause() != null ? e.getCause() : e;

                    reportArea.setText("Report could not be generated:\n\n" + cause.getMessage());
                }
            }
        };

        worker.execute();
    }

    // ================= BUILD REPORT =================

    @SuppressWarnings("unchecked")
    private void buildReport(String json) {

        Map<String, Object> root;

        try {
            root = JsonUtil.parseObjectRoot(json);
        } catch (Exception e) {

            statusLabel.setText("Invalid response from Data Engine");
            statusLabel.setForeground(RED);

            reportArea.setText("The Data Engine returned something that couldn't be read:\n\n" + json);

             return;
        }

        Object success = root.get("success");

        if (success == null || !Boolean.TRUE.equals(success)) {

            statusLabel.setText("Report generation failed");
            statusLabel.setForeground(RED);

            Object error = root.get("error");

            reportArea.setText("Data Engine Error:\n\n" + (error != null ? error : "Unknown error."));

            return;
        }

        StringBuilder sb = new StringBuilder();

        String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );

        sb.append("========================================================\n");
        sb.append("           STUDENT PERFORMANCE & ATTENDANCE REPORT\n");
        sb.append("========================================================\n");
        sb.append("Dataset:    ").append(root.get("file_name")).append("\n");
        sb.append("Generated:  ").append(timestamp).append("\n");
        sb.append("--------------------------------------------------------\n\n");

        // ---------- overview ----------

        Map<String, Object> overview = (Map<String, Object>) root.get("overview");

        if (overview != null) {

            sb.append("DATASET OVERVIEW\n");
            sb.append("----------------\n");
            sb.append(String.format("Total Rows:      %s%n", overview.get("rows")));
            sb.append(String.format("Total Columns:   %s%n", overview.get("columns")));
            sb.append(String.format("Duplicates:      %s%n", overview.get("duplicates")));
            sb.append(String.format("Missing Values:  %s%n%n", overview.get("missing_total")));
        }

        // ---------- performance distribution ----------

        Map<String, Object> distribution = (Map<String, Object>) root.get("performance_distribution");

        sb.append("PERFORMANCE DISTRIBUTION\n");
        sb.append("------------------------\n");

        if (distribution != null) {

            sb.append(String.format("Excellent (85+):    %s%n", distribution.get("Excellent")));
            sb.append(String.format("Good (70-84):       %s%n", distribution.get("Good")));
            sb.append(String.format("Average (50-69):    %s%n", distribution.get("Average")));
            sb.append(String.format("At-Risk (<50):      %s%n%n", distribution.get("At-Risk")));

        } else {

            sb.append("No marks/score column detected in this dataset.\n\n");
        }

        // ---------- attendance summary ----------

        Map<String, Object> attendance = (Map<String, Object>) root.get("attendance_summary");

        sb.append("ATTENDANCE SUMMARY\n");
        sb.append("------------------\n");

        if (attendance != null) {

            sb.append(String.format("Average Attendance: %s%%%n", attendance.get("average")));
            sb.append(String.format("Lowest Attendance:  %s%%%n", attendance.get("minimum")));
            sb.append(String.format("Highest Attendance: %s%%%n%n", attendance.get("maximum")));

        } else {

            sb.append("No attendance column detected in this dataset.\n\n");
        }

        // ---------- correlation ----------

        Object correlation = root.get("performance_attendance_correlation");

        sb.append("PERFORMANCE vs ATTENDANCE CORRELATION\n");
        sb.append("--------------------------------------\n");
        sb.append(correlation != null ? correlation.toString() : "N/A").append("\n\n");

        // ---------- column statistics ----------

        Map<String, Object> statistics = (Map<String, Object>) root.get("statistics");

        sb.append("COLUMN STATISTICS\n");
        sb.append("-----------------\n");

        if (statistics != null && !statistics.isEmpty()) {

            for (Map.Entry<String, Object> entry : statistics.entrySet()) {

                Map<String, Object> stats = (Map<String, Object>) entry.getValue();

                sb.append(String.format(
                        "%-20s mean=%-8s median=%-8s min=%-8s max=%-8s%n",
                        entry.getKey(),
                        stats.get("mean"),
                        stats.get("median"),
                        stats.get("minimum"),
                        stats.get("maximum")
                ));
            }

            sb.append("\n");

        } else {

            sb.append("No numeric columns found.\n\n");
        }

        // ---------- top performers ----------

        sb.append("TOP PERFORMERS\n");
        sb.append("--------------\n");
        appendStudentList(sb, (List<Object>) root.get("top_performers"));
        sb.append("\n");

        // ---------- at-risk students ----------

        sb.append("AT-RISK STUDENTS (TOP 5 SHOWN — see At-Risk Students page for full list)\n");
        sb.append("-------------------------------------------------------------------------\n");
        appendStudentList(sb, (List<Object>) root.get("at_risk_students"));

        sb.append("\n========================================================\n");
        sb.append("                     END OF REPORT\n");
        sb.append("========================================================\n");

        generatedReportText = sb.toString();

        reportArea.setText(generatedReportText);
        reportArea.setCaretPosition(0);

        exportButton.setEnabled(true);

        statusLabel.setText("Report generated successfully");
        statusLabel.setForeground(GREEN);
    }

    @SuppressWarnings("unchecked")
    private void appendStudentList(StringBuilder sb, List<Object> students) {

        if (students == null || students.isEmpty()) {

            sb.append("(none)\n");
            return;
        }

        for (Object studentObj : students) {

            Map<String, Object> student = (Map<String, Object>) studentObj;

            StringBuilder row = new StringBuilder();

            for (Map.Entry<String, Object> entry : student.entrySet()) {

                row.append(entry.getKey()).append("=").append(entry.getValue()).append("  ");
            }

            sb.append(row.toString().trim()).append("\n");
        }
    }

    // ================= EXPORT REPORT =================

    private void exportReport() {

        if (generatedReportText == null) {
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Report");
        chooser.setSelectedFile(new File("EduInsight_Report.txt"));

        int result = chooser.showSaveDialog(this);

        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();

        try (FileWriter writer = new FileWriter(file)) {

            writer.write(generatedReportText);

            JOptionPane.showMessageDialog(
                    this,
                    "Report saved to:\n" + file.getAbsolutePath(),
                    "Report Saved",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IOException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not save the report:\n" + e.getMessage(),
                    "Save Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ================= BUTTON STYLES =================

    private void stylePrimaryButton(JButton button) {

        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
    }

    private void styleOutlineButton(JButton button) {

        button.setBackground(WHITE);
        button.setForeground(PRIMARY);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBorder(new LineBorder(new Color(220, 223, 235)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Reports page = new Reports();
            page.setVisible(true);
        });
    }
}