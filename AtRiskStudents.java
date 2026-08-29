package eduinsight;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class AtRiskStudents extends JFrame {

    // ================= COLORS =================

    private final Color BG = new Color(245, 247, 252);
    private final Color WHITE = Color.WHITE;
    private final Color PRIMARY = new Color(99, 91, 255);
    private final Color TEXT = new Color(25, 31, 45);
    private final Color MUTED = new Color(116, 124, 142);
    private final Color GREEN = new Color(34, 197, 140);
    private final Color ORANGE = new Color(245, 158, 11);
    private final Color RED = new Color(239, 68, 68);

    // ================= LABELS =================

    private JLabel datasetLabel;
    private JLabel statusLabel;
    private JLabel countLabel;
    private JLabel thresholdLabel;

    // ================= TABLE =================

    private DefaultTableModel tableModel;

    // ================= CONSTRUCTOR =================

    public AtRiskStudents() {

        setTitle("EduInsight | At-Risk Students");

        setSize(1150, 780);
        setMinimumSize(new Dimension(1000, 650));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        main.add(createTopBar(), BorderLayout.NORTH);
        main.add(createContent(), BorderLayout.CENTER);

        setContentPane(main);

        loadAtRiskStudents();
    }

    // ================= TOP BAR =================

    private JPanel createTopBar() {

        JPanel bar = new JPanel(new BorderLayout());

        bar.setBackground(WHITE);
        bar.setBorder(new EmptyBorder(18, 28, 18, 28));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("At-Risk Students");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Students who may need additional support");
        subtitle.setForeground(MUTED);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));

        left.add(title);
        left.add(Box.createVerticalStrut(4));
        left.add(subtitle);

        JButton backButton = new JButton("← Back to Dashboard");

        styleOutlineButton(backButton);

        backButton.addActionListener(e -> {
            dispose();
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        });

        bar.add(left, BorderLayout.WEST);
        bar.add(backButton, BorderLayout.EAST);

        return bar;
    }

    // ================= CONTENT =================

    private JScrollPane createContent() {

        JPanel content = new JPanel();

        content.setBackground(BG);
        content.setBorder(new EmptyBorder(25, 30, 25, 30));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(createDatasetCard());
        content.add(Box.createVerticalStrut(20));
        content.add(createSummaryCards());
        content.add(Box.createVerticalStrut(20));
        content.add(createTablePanel());
        content.add(Box.createVerticalStrut(20));
        content.add(createStatusCard());

        JScrollPane scroll = new JScrollPane(content);

        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        return scroll;
    }

    // ================= DATASET CARD =================

    private JPanel createDatasetCard() {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
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

        return card;
    }

    // ================= SUMMARY CARDS =================

    private JPanel createSummaryCards() {

        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));

        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        countLabel = new JLabel("-");
        thresholdLabel = new JLabel("< 50");

        panel.add(createStatCard("AT-RISK STUDENTS", countLabel, RED));
        panel.add(createStatCard("RISK THRESHOLD", thresholdLabel, ORANGE));

        return panel;
    }

    private JPanel createStatCard(String title, JLabel value, Color color) {

        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(WHITE);
        card.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(MUTED);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 10));

        value.setForeground(color);
        value.setFont(new Font("SansSerif", Font.BOLD, 25));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);

        return card;
    }

    // ================= TABLE PANEL =================

    private JPanel createTablePanel() {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        card.setBackground(WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Students Needing Attention");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 15));
        title.setBorder(new EmptyBorder(0, 0, 12, 0));

        tableModel = new DefaultTableModel();

        JTable table = new JTable(tableModel);

        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setForeground(TEXT);
        table.setGridColor(new Color(235, 237, 243));

        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(245, 246, 250));
        table.getTableHeader().setForeground(TEXT);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(null);

        card.add(title, BorderLayout.NORTH);
        card.add(tableScroll, BorderLayout.CENTER);

        return card;
    }

    // ================= STATUS CARD =================

    private JPanel createStatusCard() {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
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

        return card;
    }

    // =====================================================
    // LOAD DATA
    // =====================================================

    private void loadAtRiskStudents() {

        if (!DatasetManager.hasDataset()) {

            datasetLabel.setText("No dataset selected");

            statusLabel.setText("Please upload a dataset first.");
            statusLabel.setForeground(MUTED);

            return;
        }

        String path = DatasetManager.getDatasetPath();

        datasetLabel.setText(new File(path).getName());

        statusLabel.setText("Finding at-risk students...");
        statusLabel.setForeground(PRIMARY);

        SwingWorker<String, Void> worker = new SwingWorker<>() {

            @Override
            protected String doInBackground() throws Exception {
                return PythonEngine.run(path, "at_risk");
            }

            @Override
            protected void done() {

                try {

                    String json = get();
                    applyResult(json);

                } catch (Exception e) {

                    statusLabel.setText("Failed to load at-risk students");
                    statusLabel.setForeground(RED);

                    Throwable cause = e.getCause() != null ? e.getCause() : e;

                    JTextArea area = new JTextArea(String.valueOf(cause.getMessage()));
                    area.setEditable(false);
                    area.setLineWrap(true);
                    area.setWrapStyleWord(true);

                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new Dimension(500, 220));

                    JOptionPane.showMessageDialog(
                            AtRiskStudents.this,
                            scroll,
                            "Data Engine Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }

    // ================= APPLY RESULT =================

    @SuppressWarnings("unchecked")
    private void applyResult(String json) {

        Map<String, Object> root;

        try {
            root = JsonUtil.parseObjectRoot(json);
        } catch (Exception e) {

            statusLabel.setText("Invalid response from Data Engine");
            statusLabel.setForeground(RED);

            JOptionPane.showMessageDialog(
                    this,
                    "The Data Engine returned something that couldn't be read:\n" + json,
                    "Parse Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        Object success = root.get("success");

        if (success == null || !Boolean.TRUE.equals(success)) {

            statusLabel.setText("Failed to load at-risk students");
            statusLabel.setForeground(RED);

            Object error = root.get("error");

            JOptionPane.showMessageDialog(
                    this,
                    error != null ? error.toString() : "Unknown error from Data Engine.",
                    "Data Engine Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        Object threshold = root.get("threshold");
        thresholdLabel.setText("< " + (threshold != null ? threshold.toString() : "50"));

        Object performanceColumn = root.get("performance_column");

        if (performanceColumn == null) {

            countLabel.setText("N/A");

            statusLabel.setText(
                    "No marks/score column was detected in this dataset, "
                            + "so at-risk students can't be identified."
            );

            statusLabel.setForeground(ORANGE);

            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);

            return;
        }

        Object count = root.get("at_risk_count");
        countLabel.setText(count != null ? count.toString() : "0");

        List<Object> students = (List<Object>) root.get("at_risk_students");

        populateTable(students);

        if (students == null || students.isEmpty()) {

            statusLabel.setText("No at-risk students found — great result!");
            statusLabel.setForeground(GREEN);

        } else {

            statusLabel.setText("At-risk students loaded successfully");
            statusLabel.setForeground(GREEN);
        }
    }

    @SuppressWarnings("unchecked")
    private void populateTable(List<Object> rows) {

        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        if (rows == null || rows.isEmpty()) {
            return;
        }

        Map<String, Object> firstRow = (Map<String, Object>) rows.get(0);

        String[] columns = firstRow.keySet().toArray(new String[0]);

        tableModel.setColumnIdentifiers(columns);

        for (Object rowObj : rows) {

            Map<String, Object> rowMap = (Map<String, Object>) rowObj;
            Object[] values = new Object[columns.length];

            for (int i = 0; i < columns.length; i++) {
                Object value = rowMap.get(columns[i]);
                values[i] = value != null ? value : "";
            }

            tableModel.addRow(values);
        }
    }

    // ================= BUTTON STYLE =================

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
            AtRiskStudents page = new AtRiskStudents();
            page.setVisible(true);
        });
    }
}