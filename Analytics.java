package eduinsight;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;
public class Analytics extends JFrame {

    // ================= COLORS =================

    private final Color BG = new Color(245, 247, 252);
    private final Color WHITE = Color.WHITE;
    private final Color PRIMARY = new Color(99, 91, 255);
    private final Color TEXT = new Color(25, 31, 45);
    private final Color MUTED = new Color(116, 124, 142);
    private final Color GREEN = new Color(34, 197, 140);
    private final Color ORANGE = new Color(245, 158, 11);
    private final Color RED = new Color(239, 68, 68);
    private final Color BLUE = new Color(59, 130, 246);

    // ================= LABELS =================

    private JLabel datasetLabel;
    private JLabel statusLabel;

    private JLabel excellentLabel;
    private JLabel goodLabel;
    private JLabel averageLabel;
    private JLabel atRiskLabel;

    private JLabel attendanceAvgLabel;
    private JLabel attendanceMinLabel;
    private JLabel attendanceMaxLabel;
    private JLabel correlationLabel;

    // ================= TABLES =================

    private DefaultTableModel statisticsModel;
    private DefaultTableModel topPerformersModel;
    private DefaultTableModel atRiskModel;

    // ================= CONSTRUCTOR =================

    public Analytics() {

        setTitle("EduInsight | Analytics");

        setSize(1250, 800);
        setMinimumSize(new Dimension(1050, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        main.add(createTopBar(), BorderLayout.NORTH);
        main.add(createContent(), BorderLayout.CENTER);

        setContentPane(main);

        loadAnalytics();
    }

    // ================= TOP BAR =================

    private JPanel createTopBar() {

        JPanel bar = new JPanel(new BorderLayout());

        bar.setBackground(WHITE);
        bar.setBorder(new EmptyBorder(18, 28, 18, 28));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Analytics");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Performance & attendance insights");
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

        content.add(createPerformanceDistributionCards());
        content.add(Box.createVerticalStrut(20));

        content.add(createAttendanceCards());
        content.add(Box.createVerticalStrut(20));

        content.add(createStatisticsPanel());
        content.add(Box.createVerticalStrut(20));

        content.add(createStudentTablesPanel());
        content.add(Box.createVerticalStrut(20));

        content.add(createStatusCard());

        JScrollPane scroll = new JScrollPane(content);

        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

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

    // ================= PERFORMANCE DISTRIBUTION =================

    private JPanel createPerformanceDistributionCards() {

        JPanel wrapper = new JPanel(new BorderLayout());

        wrapper.setOpaque(false);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        JLabel heading = new JLabel("Performance Distribution");

        heading.setForeground(TEXT);
        heading.setFont(new Font("SansSerif", Font.BOLD, 14));
        heading.setBorder(new EmptyBorder(0, 2, 8, 0));

        JPanel cards = new JPanel(new GridLayout(1, 4, 15, 0));

        cards.setOpaque(false);

        excellentLabel = new JLabel("-");
        goodLabel = new JLabel("-");
        averageLabel = new JLabel("-");
        atRiskLabel = new JLabel("-");

        cards.add(
                createStatCard(
                        "EXCELLENT (85+)",
                        excellentLabel,
                        GREEN
                )
        );

        cards.add(
                createStatCard(
                        "GOOD (70-84)",
                        goodLabel,
                        BLUE
                )
        );

        cards.add(
                createStatCard(
                        "AVERAGE (50-69)",
                        averageLabel,
                        ORANGE
                )
        );

        cards.add(
                createStatCard(
                        "AT-RISK (<50)",
                        atRiskLabel,
                        RED
                )
        );

        wrapper.add(heading, BorderLayout.NORTH);
        wrapper.add(cards, BorderLayout.CENTER);

        return wrapper;
    }

    // ================= ATTENDANCE CARDS =================

    private JPanel createAttendanceCards() {

        JPanel wrapper = new JPanel(new BorderLayout());

        wrapper.setOpaque(false);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        JLabel heading = new JLabel("Attendance Summary");

        heading.setForeground(TEXT);
        heading.setFont(new Font("SansSerif", Font.BOLD, 14));
        heading.setBorder(new EmptyBorder(0, 2, 8, 0));

        JPanel cards = new JPanel(new GridLayout(1, 3, 15, 0));

        cards.setOpaque(false);

        attendanceAvgLabel = new JLabel("-");
        attendanceMinLabel = new JLabel("-");
        attendanceMaxLabel = new JLabel("-");

        cards.add(
                createStatCard(
                        "AVERAGE ATTENDANCE",
                        attendanceAvgLabel,
                        PRIMARY
                )
        );

        cards.add(
                createStatCard(
                        "LOWEST ATTENDANCE",
                        attendanceMinLabel,
                        RED
                )
        );

        cards.add(
                createStatCard(
                        "HIGHEST ATTENDANCE",
                        attendanceMaxLabel,
                        GREEN
                )
        );

        wrapper.add(heading, BorderLayout.NORTH);
        wrapper.add(cards, BorderLayout.CENTER);

        return wrapper;
    }

    // ================= STAT CARD =================

    private JPanel createStatCard(
            String title,
            JLabel value,
            Color color
    ) {

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

    // ================= STATISTICS PANEL =================

    private JPanel createStatisticsPanel() {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));
        card.setBackground(WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());

        header.setOpaque(false);

        JLabel title = new JLabel("Column Statistics");

        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 15));

        correlationLabel = new JLabel(
                "Performance ↔ Attendance correlation: -"
        );

        correlationLabel.setForeground(MUTED);
        correlationLabel.setFont(
                new Font("SansSerif", Font.PLAIN, 11)
        );

        header.add(title, BorderLayout.WEST);
        header.add(correlationLabel, BorderLayout.EAST);

        statisticsModel = new DefaultTableModel(
                new Object[]{
                        "Column",
                        "Mean",
                        "Median",
                        "Min",
                        "Max"
                },
                0
        );

        JTable table = new JTable(statisticsModel);

        styleTable(table);

        JScrollPane scroll = new JScrollPane(table);

        scroll.setBorder(
                new EmptyBorder(12, 0, 0, 0)
        );

        card.add(header, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        return card;
    }

    // ================= STUDENT TABLES =================

    private JPanel createStudentTablesPanel() {

        JPanel panel = new JPanel(
                new GridLayout(1, 2, 16, 0)
        );

        panel.setOpaque(false);
        panel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 260)
        );

        panel.add(
                createStudentTableCard(
                        "Top Performers",
                        GREEN,
                        true
                )
        );

        panel.add(
                createStudentTableCard(
                        "At-Risk Students",
                        RED,
                        false
                )
        );

        return panel;
    }

    private JPanel createStudentTableCard(
            String title,
            Color accent,
            boolean isTop
    ) {

        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(WHITE);
        card.setBorder(
                new EmptyBorder(18, 18, 18, 18)
        );

        JLabel titleLabel = new JLabel(title);

        titleLabel.setForeground(accent);
        titleLabel.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        titleLabel.setBorder(
                new EmptyBorder(0, 0, 10, 0)
        );

        DefaultTableModel model =
                new DefaultTableModel();

        JTable table = new JTable(model);

        styleTable(table);

        if (isTop) {
            topPerformersModel = model;
        } else {
            atRiskModel = model;
        }

        JScrollPane scroll = new JScrollPane(table);

        scroll.setBorder(null);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        return card;
    }

    // ================= TABLE STYLE =================

    private void styleTable(JTable table) {

        table.setRowHeight(28);

        table.setFont(
                new Font("SansSerif", Font.PLAIN, 12)
        );

        table.setForeground(TEXT);

        table.setGridColor(
                new Color(235, 237, 243)
        );

        table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );

        table.getTableHeader().setBackground(
                new Color(245, 246, 250)
        );

        table.getTableHeader().setForeground(TEXT);
    }

    // ================= STATUS CARD =================

    private JPanel createStatusCard() {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 80)
        );

        card.setBackground(WHITE);

        card.setBorder(
                new EmptyBorder(15, 20, 15, 20)
        );

        JLabel title = new JLabel("STATUS");

        title.setForeground(MUTED);

        title.setFont(
                new Font("SansSerif", Font.BOLD, 10)
        );

        statusLabel = new JLabel(
                "Waiting for dataset..."
        );

        statusLabel.setForeground(MUTED);

        statusLabel.setFont(
                new Font("SansSerif", Font.BOLD, 13)
        );

        JPanel left = new JPanel();

        left.setOpaque(false);

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        left.add(title);

        left.add(
                Box.createVerticalStrut(6)
        );

        left.add(statusLabel);

        card.add(left, BorderLayout.WEST);

        return card;
    }

    // =====================================================
    // LOAD ANALYTICS
    // =====================================================

    private void loadAnalytics() {

        if (!DatasetManager.hasDataset()) {

            datasetLabel.setText(
                    "No dataset selected"
            );

            statusLabel.setText(
                    "Please upload a dataset first."
            );

            statusLabel.setForeground(MUTED);

            return;
        }

        String path =
                DatasetManager.getDatasetPath();

        datasetLabel.setText(
                new File(path).getName()
        );

        statusLabel.setText(
                "Running analytics..."
        );

        statusLabel.setForeground(PRIMARY);

        SwingWorker<String, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected String doInBackground()
                            throws Exception {

                        return PythonEngine.run(
                                path,
                                "analytics"
                        );
                    }

                    @Override
                    protected void done() {

                        try {

                            String json = get();

                            applyAnalytics(json);

                        } catch (Exception e) {

                            statusLabel.setText(
                                    "Failed to run analytics"
                            );

                            statusLabel.setForeground(RED);

                            Throwable cause =
                                    e.getCause() != null
                                            ? e.getCause()
                                            : e;

                            JTextArea area =
                                    new JTextArea(
                                            String.valueOf(
                                                    cause.getMessage()
                                            )
                                    );

                            area.setEditable(false);
                            area.setLineWrap(true);
                            area.setWrapStyleWord(true);

                            JScrollPane scroll =
                                    new JScrollPane(area);

                            scroll.setPreferredSize(
                                    new Dimension(500, 220)
                            );

                            JOptionPane.showMessageDialog(
                                    Analytics.this,
                                    scroll,
                                    "Data Engine Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                };

        worker.execute();
    }

    // =====================================================
    // APPLY RESULT
    // =====================================================

    @SuppressWarnings("unchecked")
    private void applyAnalytics(String json) {

        Map<String, Object> root;

        // ================= JSON PARSING =================

        try {

            Gson gson = new Gson();
            java.lang.reflect.Type type =
                    new TypeToken<Map<String, Object>>() {
                    }.getType();

            root = gson.fromJson(json, type);

        } catch (Exception e) {

            statusLabel.setText(
                    "Invalid response from Data Engine"
            );

            statusLabel.setForeground(RED);

            JOptionPane.showMessageDialog(
                    this,
                    "The Data Engine returned something "
                            + "that couldn't be read:\n\n"
                            + json,
                    "Parse Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        if (root == null) {

            statusLabel.setText(
                    "Empty response from Data Engine"
            );

            statusLabel.setForeground(RED);

            return;
        }

        // ================= SUCCESS =================

        Object success = root.get("success");

        if (success == null
                || !Boolean.TRUE.equals(success)) {

            statusLabel.setText(
                    "Analytics failed"
            );

            statusLabel.setForeground(RED);

            Object error = root.get("error");

            JOptionPane.showMessageDialog(
                    this,
                    error != null
                            ? error.toString()
                            : "Unknown error from Data Engine.",
                    "Data Engine Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // ================= PERFORMANCE DISTRIBUTION =================

        Map<String, Object> distribution =
                (Map<String, Object>)
                        root.get(
                                "performance_distribution"
                        );

        if (distribution != null) {

            excellentLabel.setText(
                    String.valueOf(
                            distribution.get("Excellent")
                    )
            );

            goodLabel.setText(
                    String.valueOf(
                            distribution.get("Good")
                    )
            );

            averageLabel.setText(
                    String.valueOf(
                            distribution.get("Average")
                    )
            );

            atRiskLabel.setText(
                    String.valueOf(
                            distribution.get("At-Risk")
                    )
            );

        } else {

            excellentLabel.setText("N/A");
            goodLabel.setText("N/A");
            averageLabel.setText("N/A");
            atRiskLabel.setText("N/A");
        }

        // ================= ATTENDANCE SUMMARY =================

        Map<String, Object> attendance =
                (Map<String, Object>)
                        root.get(
                                "attendance_summary"
                        );

        if (attendance != null) {

            attendanceAvgLabel.setText(
                    formatValue(
                            attendance.get("average")
                    ) + "%"
            );

            attendanceMinLabel.setText(
                    formatValue(
                            attendance.get("minimum")
                    ) + "%"
            );

            attendanceMaxLabel.setText(
                    formatValue(
                            attendance.get("maximum")
                    ) + "%"
            );

        } else {

            attendanceAvgLabel.setText("N/A");
            attendanceMinLabel.setText("N/A");
            attendanceMaxLabel.setText("N/A");
        }

        // ================= CORRELATION =================

        Object correlation =
                root.get(
                        "performance_attendance_correlation"
                );

        correlationLabel.setText(
                "Performance ↔ Attendance correlation: "
                        + (
                        correlation != null
                                ? formatValue(correlation)
                                : "N/A"
                )
        );

        // ================= STATISTICS TABLE =================

        Map<String, Object> statistics =
                (Map<String, Object>)
                        root.get("statistics");

        statisticsModel.setRowCount(0);

        if (statistics != null) {

            for (
                    Map.Entry<String, Object> entry
                    : statistics.entrySet()
            ) {

                if (!(entry.getValue()
                        instanceof Map)) {
                    continue;
                }

                Map<String, Object> stats =
                        (Map<String, Object>)
                                entry.getValue();

                statisticsModel.addRow(
                        new Object[]{
                                entry.getKey(),
                                formatValue(
                                        stats.get("mean")
                                ),
                                formatValue(
                                        stats.get("median")
                                ),
                                formatValue(
                                        stats.get("minimum")
                                ),
                                formatValue(
                                        stats.get("maximum")
                                )
                        }
                );
            }
        }

        // ================= TOP PERFORMERS =================

        populateStudentTable(
                topPerformersModel,
                (List<Object>)
                        root.get("top_performers")
        );

        // ================= AT-RISK STUDENTS =================

        populateStudentTable(
                atRiskModel,
                (List<Object>)
                        root.get("at_risk_students")
        );

        // ================= STATUS =================

        Object performanceColumn =
                root.get("performance_column");

        if (performanceColumn == null) {

            statusLabel.setText(
                    "Analytics loaded, but no marks/score "
                            + "column was detected "
                            + "(distribution and student "
                            + "rankings are unavailable)."
            );

            statusLabel.setForeground(ORANGE);

        } else {

            statusLabel.setText(
                    "Analytics loaded successfully"
            );

            statusLabel.setForeground(GREEN);
        }
    }

    // =====================================================
    // FORMAT JSON NUMBER
    // =====================================================

    private String formatValue(Object value) {

        if (value == null) {
            return "N/A";
        }

        if (value instanceof Number) {

            double number =
                    ((Number) value).doubleValue();

            if (Double.isNaN(number)
                    || Double.isInfinite(number)) {
                return "N/A";
            }

            if (number == Math.floor(number)) {
                return String.valueOf(
                        (long) number
                );
            }

            return String.format(
                    "%.2f",
                    number
            );
        }

        return value.toString();
    }

    // =====================================================
    // POPULATE STUDENT TABLE
    // =====================================================

    @SuppressWarnings("unchecked")
    private void populateStudentTable(
            DefaultTableModel model,
            List<Object> rows
    ) {

        if (model == null) {
            return;
        }

        model.setRowCount(0);
        model.setColumnCount(0);

        if (rows == null
                || rows.isEmpty()) {
            return;
        }

        if (!(rows.get(0) instanceof Map)) {
            return;
        }

        Map<String, Object> firstRow =
                (Map<String, Object>)
                        rows.get(0);

        String[] columns =
                firstRow.keySet()
                        .toArray(new String[0]);

        model.setColumnIdentifiers(
                columns
        );

        for (Object rowObj : rows) {

            if (!(rowObj instanceof Map)) {
                continue;
            }

            Map<String, Object> rowMap =
                    (Map<String, Object>)
                            rowObj;

            Object[] values =
                    new Object[columns.length];

            for (int i = 0;
                 i < columns.length;
                 i++) {

                Object value =
                        rowMap.get(columns[i]);

                values[i] =
                        value != null
                                ? formatValue(value)
                                : "";
            }

            model.addRow(values);
        }
    }

    // ================= BUTTON STYLE =================

    private void styleOutlineButton(
            JButton button
    ) {

        button.setBackground(WHITE);

        button.setForeground(PRIMARY);

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        button.setBorder(
                new LineBorder(
                        new Color(220, 223, 235)
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Analytics page =
                    new Analytics();

            page.setVisible(true);
        });
    }
}

