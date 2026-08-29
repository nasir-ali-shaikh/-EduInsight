package eduinsight;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.util.List;
import java.util.Map;

public class Visualizations extends JFrame {

    // =====================================================
    // COLORS
    // =====================================================

    private final Color BG = new Color(245, 247, 252);
    private final Color WHITE = Color.WHITE;

    private final Color PRIMARY = new Color(99, 91, 255);
    private final Color TEXT = new Color(25, 31, 45);
    private final Color MUTED = new Color(116, 124, 142);

    private final Color GREEN = new Color(34, 197, 140);
    private final Color ORANGE = new Color(245, 158, 11);
    private final Color RED = new Color(239, 68, 68);
    private final Color BLUE = new Color(59, 130, 246);

    // =====================================================
    // LABELS
    // =====================================================

    private JLabel datasetLabel;
    private JLabel statusLabel;

    private JLabel rowsLabel;
    private JLabel columnsLabel;
    private JLabel performanceLabel;
    private JLabel attendanceLabel;

    // =====================================================
    // DATA
    // =====================================================

    private Map<String, Object> visualizationData;

    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public Visualizations() {

        setTitle("EduInsight | Visualizations");

        setSize(1350, 850);
        setMinimumSize(new Dimension(1100, 700));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        JPanel main = new JPanel(
                new BorderLayout()
        );

        main.setBackground(BG);

        main.add(
                createTopBar(),
                BorderLayout.NORTH
        );

        main.add(
                createContent(),
                BorderLayout.CENTER
        );

        setContentPane(main);

        loadVisualizations();
    }

    // =====================================================
    // TOP BAR
    // =====================================================

    private JPanel createTopBar() {

        JPanel bar = new JPanel(
                new BorderLayout()
        );

        bar.setBackground(WHITE);

        bar.setBorder(
                new EmptyBorder(
                        16,
                        28,
                        16,
                        28
                )
        );

        // LEFT

        JPanel left = new JPanel();

        left.setOpaque(false);

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title = new JLabel(
                "Visualizations"
        );

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        JLabel subtitle = new JLabel(
                "Interactive charts & visual insights"
        );

        subtitle.setForeground(MUTED);

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        11
                )
        );

        left.add(title);

        left.add(
                Box.createVerticalStrut(4)
        );

        left.add(subtitle);

        // RIGHT

        JButton backButton =
                new JButton(
                        "← Back to Dashboard"
                );

        styleOutlineButton(backButton);

        backButton.addActionListener(e -> {

            dispose();

            Dashboard dashboard =
                    new Dashboard();

            dashboard.setVisible(true);
        });

        bar.add(
                left,
                BorderLayout.WEST
        );

        bar.add(
                backButton,
                BorderLayout.EAST
        );

        return bar;
    }

    // =====================================================
    // CONTENT
    // =====================================================

    private JScrollPane createContent() {

        JPanel content = new JPanel();

        content.setBackground(BG);

        content.setBorder(
                new EmptyBorder(
                        22,
                        28,
                        28,
                        28
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        // DATASET

        content.add(
                createDatasetCard()
        );

        content.add(
                Box.createVerticalStrut(18)
        );

        // KPI

        content.add(
                createKpiCards()
        );

        content.add(
                Box.createVerticalStrut(20)
        );

        // CHARTS

        content.add(
                createChartsPanel()
        );

        content.add(
                Box.createVerticalStrut(20)
        );

        // STATUS

        content.add(
                createStatusCard()
        );

        JScrollPane scroll =
                new JScrollPane(content);

        scroll.setBorder(null);

        scroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        scroll.getViewport()
                .setBackground(BG);

        return scroll;
    }

    // =====================================================
    // DATASET CARD
    // =====================================================

    private JPanel createDatasetCard() {

        JPanel card =
                new RoundedPanel(
                        WHITE,
                        18
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        85
                )
        );

        card.setBorder(
                new EmptyBorder(
                        16,
                        20,
                        16,
                        20
                )
        );

        JPanel left = new JPanel();

        left.setOpaque(false);

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel heading =
                new JLabel(
                        "CURRENT DATASET"
                );

        heading.setForeground(MUTED);

        heading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        datasetLabel =
                new JLabel(
                        "No dataset selected"
                );

        datasetLabel.setForeground(TEXT);

        datasetLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        left.add(heading);

        left.add(
                Box.createVerticalStrut(5)
        );

        left.add(datasetLabel);

        card.add(
                left,
                BorderLayout.WEST
        );

        return card;
    }

    // =====================================================
    // KPI CARDS
    // =====================================================

    private JPanel createKpiCards() {

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        cards.setOpaque(false);

        rowsLabel =
                new JLabel("-");

        columnsLabel =
                new JLabel("-");

        performanceLabel =
                new JLabel("-");

        attendanceLabel =
                new JLabel("-");

        cards.add(
                createKpiCard(
                        "TOTAL RECORDS",
                        rowsLabel,
                        BLUE
                )
        );

        cards.add(
                createKpiCard(
                        "TOTAL COLUMNS",
                        columnsLabel,
                        PRIMARY
                )
        );

        cards.add(
                createKpiCard(
                        "AVG PERFORMANCE",
                        performanceLabel,
                        GREEN
                )
        );

        cards.add(
                createKpiCard(
                        "AVG ATTENDANCE",
                        attendanceLabel,
                        ORANGE
                )
        );

        cards.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        110
                )
        );

        return cards;
    }

    private JPanel createKpiCard(
            String title,
            JLabel value,
            Color color
    ) {

        JPanel card =
                new RoundedPanel(
                        WHITE,
                        16
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        16,
                        18,
                        16,
                        18
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(MUTED);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        value.setForeground(color);

        value.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        25
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                value,
                BorderLayout.CENTER
        );

        return card;
    }

    // =====================================================
    // CHARTS PANEL
    // =====================================================

    private JPanel createChartsPanel() {

        JPanel charts =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                18,
                                18
                        )
                );

        charts.setOpaque(false);

        // 1

        charts.add(
                createChartCard(
                        "Performance Distribution",
                        new PerformanceBarChart()
                )
        );

        // 2

        charts.add(
                createChartCard(
                        "Performance Trend",
                        new PerformanceLineChart()
                )
        );

        // 3

        charts.add(
                createChartCard(
                        "Performance Categories",
                        new PerformancePieChart()
                )
        );

        // 4

        charts.add(
                createChartCard(
                        "Marks Histogram",
                        new PerformanceHistogram()
                )
        );

        // 5

        charts.add(
                createChartCard(
                        "Performance vs Attendance",
                        new PerformanceScatterChart()
                )
        );

        // 6

        charts.add(
                createChartCard(
                        "Attendance Overview",
                        new AttendanceChart()
                )
        );

        charts.setPreferredSize(
                new Dimension(
                        900,
                        900
                )
        );

        charts.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        900
                )
        );

        return charts;
    }

    // =====================================================
    // CHART CARD
    // =====================================================

    private JPanel createChartCard(
            String title,
            JPanel chart
    ) {

        JPanel card =
                new RoundedPanel(
                        WHITE,
                        18
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setBorder(
                new EmptyBorder(
                        16,
                        18,
                        16,
                        18
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setForeground(TEXT);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                chart,
                BorderLayout.CENTER
        );

        return card;
    }

    // =====================================================
    // LOAD DATA
    // =====================================================

    private void loadVisualizations() {

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

        SwingWorker<String, Void> worker =
                new SwingWorker<>() {

                    @Override
                    protected String doInBackground()
                            throws Exception {

                        return PythonEngine.run(
                                path,
                                "visualization"
                        );
                    }

                    @Override
                    protected void done() {

                        try {

                            String json = get();

                            parseVisualizationData(json);

                        } catch (Exception e) {

                            statusLabel.setText(
                                    "Visualization engine failed"
                            );

                            statusLabel.setForeground(RED);

                            Throwable cause =
                                    e.getCause() != null
                                            ? e.getCause()
                                            : e;

                            JOptionPane.showMessageDialog(
                                    Visualizations.this,
                                    cause.getMessage(),
                                    "Visualization Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                };

        worker.execute();
    }

    // =====================================================
    // PARSE DATA
    // =====================================================

    @SuppressWarnings("unchecked")
    private void parseVisualizationData(
            String json
    ) {

        try {

            Gson gson = new Gson();

            // NOTE: fully-qualified here on purpose.
            // Visualizations extends JFrame -> Frame -> Window,
            // and java.awt.Window declares a nested class also
            // named "Type" (java.awt.Window.Type). An inherited
            // member type takes priority over an imported type
            // with the same simple name, so a plain "Type type = ..."
            // would resolve to java.awt.Window.Type instead of
            // java.lang.reflect.Type and fail to compile.

            java.lang.reflect.Type type =
                    new TypeToken<
                            Map<String, Object>
                            >() {
                    }.getType();

            visualizationData =
                    gson.fromJson(
                            json,
                            type
                    );

            if (visualizationData == null) {
                throw new Exception(
                        "Empty response from Python."
                );
            }

            Object success =
                    visualizationData.get(
                            "success"
                    );

            if (!Boolean.TRUE.equals(success)) {

                throw new Exception(
                        String.valueOf(
                                visualizationData.get(
                                        "error"
                                )
                        )
                );
            }

            updateKpis();

            statusLabel.setText(
                    "Visualization data loaded successfully"
            );

            statusLabel.setForeground(GREEN);

            repaint();

        } catch (Exception e) {

            statusLabel.setText(
                    "Unable to read visualization data"
            );

            statusLabel.setForeground(RED);

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Parse Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =====================================================
    // UPDATE KPIs
    // =====================================================

    @SuppressWarnings("unchecked")
    private void updateKpis() {

        Map<String, Object> overview =
                (Map<String, Object>)
                        visualizationData.get(
                                "overview"
                        );

        if (overview != null) {

            rowsLabel.setText(
                    formatValue(
                            overview.get("rows")
                    )
            );

            columnsLabel.setText(
                    formatValue(
                            overview.get("columns")
                    )
            );
        }

        Object performance =
                visualizationData.get(
                        "average_performance"
                );

        Object attendance =
                visualizationData.get(
                        "average_attendance"
                );

        performanceLabel.setText(
                performance != null
                        ? formatValue(performance) + "%"
                        : "N/A"
        );

        attendanceLabel.setText(
                attendance != null
                        ? formatValue(attendance) + "%"
                        : "N/A"
        );
    }

    // =====================================================
    // STATUS CARD
    // =====================================================

    private JPanel createStatusCard() {

        JPanel card =
                new RoundedPanel(
                        WHITE,
                        16
                );

        card.setLayout(
                new BorderLayout()
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        65
                )
        );

        card.setBorder(
                new EmptyBorder(
                        14,
                        20,
                        14,
                        20
                )
        );

        JLabel title =
                new JLabel("STATUS");

        title.setForeground(MUTED);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        statusLabel =
                new JLabel(
                        "Loading visualization data..."
                );

        statusLabel.setForeground(PRIMARY);

        statusLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
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
                Box.createVerticalStrut(4)
        );

        left.add(statusLabel);

        card.add(
                left,
                BorderLayout.WEST
        );

        return card;
    }

    // =====================================================
    // PERFORMANCE BAR CHART
    // =====================================================

    private class PerformanceBarChart
            extends JPanel {

        PerformanceBarChart() {
            setOpaque(false);
            setPreferredSize(new Dimension(420, 260));
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int[] values =
                    getDistributionValues();

            String[] labels = {
                    "Excellent",
                    "Good",
                    "Average",
                    "At-Risk"
            };

            int width = getWidth();
            int height = getHeight();

            int left = 45;
            int bottom = 35;
            int top = 20;

            int chartHeight =
                    height - top - bottom;

            int max = 1;

            for (int value : values) {
                max = Math.max(max, value);
            }

            g2.setColor(
                    new Color(235, 237, 243)
            );

            g2.drawLine(
                    left,
                    top,
                    left,
                    height - bottom
            );

            g2.drawLine(
                    left,
                    height - bottom,
                    width - 20,
                    height - bottom
            );

            int barWidth = 45;

            int gap = 35;

            int startX = 65;

            Color[] colors = {
                    GREEN,
                    BLUE,
                    ORANGE,
                    RED
            };

            for (int i = 0;
                 i < values.length;
                 i++) {

                int barHeight =
                        (int)
                                (
                                        (double)
                                                values[i]
                                                / max
                                                * (
                                                chartHeight
                                                        - 20
                                        )
                                );

                int x =
                        startX
                                + i
                                * (
                                barWidth
                                        + gap
                        );

                int y =
                        height
                                - bottom
                                - barHeight;

                g2.setColor(
                        colors[i]
                );

                g2.fillRoundRect(
                        x,
                        y,
                        barWidth,
                        barHeight,
                        10,
                        10
                );

                g2.setColor(TEXT);

                g2.setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                10
                        )
                );

                String value =
                        String.valueOf(
                                values[i]
                        );

                int valueWidth =
                        g2.getFontMetrics()
                                .stringWidth(
                                        value
                                );

                g2.drawString(
                        value,
                        x
                                + barWidth / 2
                                - valueWidth / 2,
                        y - 5
                );

                g2.setColor(MUTED);

                String label =
                        labels[i];

                int labelWidth =
                        g2.getFontMetrics()
                                .stringWidth(
                                        label
                                );

                g2.drawString(
                        label,
                        x
                                + barWidth / 2
                                - labelWidth / 2,
                        height - 12
                );
            }

            g2.dispose();
        }
    }

    // =====================================================
    // PERFORMANCE LINE CHART
    // =====================================================

    private class PerformanceLineChart
            extends JPanel {

        PerformanceLineChart() {
            setOpaque(false);
            setPreferredSize(new Dimension(420, 260));
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            double[] values =
                    getPerformanceValues();

            if (values.length == 0) {

                drawNoData(g2);

                g2.dispose();

                return;
            }

            int width = getWidth();
            int height = getHeight();

            int left = 40;
            int right = 20;
            int top = 20;
            int bottom = 35;

            int chartWidth =
                    width - left - right;

            int chartHeight =
                    height - top - bottom;

            g2.setColor(
                    new Color(235, 237, 243)
            );

            for (int i = 0; i <= 4; i++) {

                int y =
                        top
                                + i
                                * chartHeight
                                / 4;

                g2.drawLine(
                        left,
                        y,
                        width - right,
                        y
                );
            }

            int[] x =
                    new int[values.length];

            int[] y =
                    new int[values.length];

            for (int i = 0;
                 i < values.length;
                 i++) {

                x[i] =
                        left
                                + i
                                * chartWidth
                                / Math.max(
                                1,
                                values.length - 1
                        );

                y[i] =
                        top
                                + chartHeight
                                - (int)
                                (
                                        values[i]
                                                / 100.0
                                                * chartHeight
                                );
            }

            g2.setColor(PRIMARY);

            g2.setStroke(
                    new BasicStroke(
                            3,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            for (int i = 0;
                 i < values.length - 1;
                 i++) {

                g2.drawLine(
                        x[i],
                        y[i],
                        x[i + 1],
                        y[i + 1]
                );
            }

            g2.setFont(
                    new Font(
                            "SansSerif",
                            Font.PLAIN,
                            9
                    )
            );

            for (int i = 0;
                 i < values.length;
                 i++) {

                g2.setColor(WHITE);

                g2.fillOval(
                        x[i] - 5,
                        y[i] - 5,
                        10,
                        10
                );

                g2.setColor(PRIMARY);

                g2.fillOval(
                        x[i] - 3,
                        y[i] - 3,
                        6,
                        6
                );

                if (i < 12) {

                    g2.setColor(MUTED);

                    g2.drawString(
                            String.valueOf(
                                    i + 1
                            ),
                            x[i] - 3,
                            height - 10
                    );
                }
            }

            g2.dispose();
        }
    }

    // =====================================================
    // PIE CHART
    // =====================================================

    private class PerformancePieChart
            extends JPanel {

        PerformancePieChart() {
            setOpaque(false);
            setPreferredSize(new Dimension(420, 260));
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int[] values =
                    getDistributionValues();

            int total = 0;

            for (int value : values) {
                total += value;
            }

            if (total == 0) {

                drawNoData(g2);

                g2.dispose();

                return;
            }

            int diameter =
                    Math.min(
                            155,
                            getHeight() - 30
                    );

            int x = 25;

            int y =
                    getHeight() / 2
                            - diameter / 2;

            Color[] colors = {
                    GREEN,
                    BLUE,
                    ORANGE,
                    RED
            };

            String[] labels = {
                    "Excellent",
                    "Good",
                    "Average",
                    "At-Risk"
            };

            int startAngle = 90;

            for (int i = 0;
                 i < values.length;
                 i++) {

                int angle =
                        (int)
                                Math.round(
                                        values[i]
                                                * 360.0
                                                / total
                                );

                g2.setColor(
                        colors[i]
                );

                g2.fillArc(
                        x,
                        y,
                        diameter,
                        diameter,
                        startAngle,
                        -angle
                );

                startAngle -= angle;
            }

            int legendX =
                    x + diameter + 25;

            int legendY = 35;

            for (int i = 0;
                 i < labels.length;
                 i++) {

                g2.setColor(
                        colors[i]
                );

                g2.fillRoundRect(
                        legendX,
                        legendY + i * 30,
                        12,
                        12,
                        4,
                        4
                );

                g2.setColor(TEXT);

                g2.setFont(
                        new Font(
                                "SansSerif",
                                Font.PLAIN,
                                11
                        )
                );

                g2.drawString(
                        labels[i]
                                + "  "
                                + values[i],
                        legendX + 20,
                        legendY
                                + 10
                                + i * 30
                );
            }

            g2.dispose();
        }
    }

    // =====================================================
    // HISTOGRAM
    // =====================================================

    private class PerformanceHistogram
            extends JPanel {

        PerformanceHistogram() {
            setOpaque(false);
            setPreferredSize(new Dimension(420, 260));
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            double[] values =
                    getPerformanceValues();

            if (values.length == 0) {

                drawNoData(g2);

                g2.dispose();

                return;
            }

            int[] bins =
                    new int[10];

            for (double value : values) {

                int index =
                        (int)
                                Math.floor(
                                        value / 10
                                );

                index =
                        Math.max(
                                0,
                                Math.min(
                                        9,
                                        index
                                )
                        );

                bins[index]++;
            }

            int max = 1;

            for (int value : bins) {
                max = Math.max(max, value);
            }

            int left = 35;
            int bottom = 35;
            int top = 20;

            int chartWidth =
                    getWidth() - left - 20;

            int chartHeight =
                    getHeight() - top - bottom;

            int barWidth =
                    Math.max(
                            10,
                            chartWidth / 10 - 4
                    );

            for (int i = 0;
                 i < bins.length;
                 i++) {

                int barHeight =
                        (int)
                                (
                                        (double)
                                                bins[i]
                                                / max
                                                * (
                                                chartHeight
                                                        - 10
                                        )
                                );

                int x =
                        left
                                + i
                                * (
                                chartWidth
                                        / 10
                        );

                int y =
                        getHeight()
                                - bottom
                                - barHeight;

                g2.setColor(BLUE);

                g2.fillRect(
                        x,
                        y,
                        barWidth,
                        barHeight
                );

                g2.setColor(MUTED);

                g2.setFont(
                        new Font(
                                "SansSerif",
                                Font.PLAIN,
                                8
                        )
                );

                g2.drawString(
                        String.valueOf(
                                i * 10
                        ),
                        x,
                        getHeight() - 12
                );
            }

            g2.dispose();
        }
    }

    // =====================================================
    // SCATTER CHART
    // =====================================================

    private class PerformanceScatterChart
            extends JPanel {

        PerformanceScatterChart() {
            setOpaque(false);
            setPreferredSize(new Dimension(420, 260));
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            double[][] points =
                    getScatterValues();

            if (points.length == 0) {

                drawNoData(g2);

                g2.dispose();

                return;
            }

            int left = 40;
            int bottom = 30;
            int top = 15;
            int right = 20;

            int width =
                    getWidth()
                            - left
                            - right;

            int height =
                    getHeight()
                            - top
                            - bottom;

            g2.setColor(
                    new Color(235, 237, 243)
            );

            for (int i = 0;
                 i <= 4;
                 i++) {

                int y =
                        top
                                + i * height / 4;

                int x =
                        left
                                + i * width / 4;

                g2.drawLine(
                        left,
                        y,
                        getWidth() - right,
                        y
                );

                g2.drawLine(
                        x,
                        top,
                        x,
                        getHeight() - bottom
                );
            }

            g2.setColor(BLUE);

            for (double[] point : points) {

                if (point.length < 2) {
                    continue;
                }

                double performance =
                        point[0];

                double attendance =
                        point[1];

                int x =
                        left
                                + (int)
                                (
                                        performance
                                                / 100.0
                                                * width
                                );

                int y =
                        getHeight()
                                - bottom
                                - (int)
                                (
                                        attendance
                                                / 100.0
                                                * height
                                );

                Shape circle =
                        new Ellipse2D.Double(
                                x - 4,
                                y - 4,
                                8,
                                8
                        );

                g2.fill(circle);
            }

            g2.setColor(MUTED);

            g2.setFont(
                    new Font(
                            "SansSerif",
                            Font.PLAIN,
                            9
                    )
            );

            g2.drawString(
                    "Performance",
                    getWidth() / 2 - 30,
                    getHeight() - 5
            );

            g2.rotate(
                    -Math.PI / 2
            );

            g2.drawString(
                    "Attendance",
                    -getHeight() / 2 - 25,
                    12
            );

            g2.dispose();
        }
    }

    // =====================================================
    // ATTENDANCE CHART
    // =====================================================

    private class AttendanceChart
            extends JPanel {

        AttendanceChart() {
            setOpaque(false);
            setPreferredSize(new Dimension(420, 260));
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            super.paintComponent(g);

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            double[] values =
                    getAttendanceValues();

            if (values.length == 0) {

                drawNoData(g2);

                g2.dispose();

                return;
            }

            int left = 35;
            int bottom = 35;
            int top = 20;

            int chartWidth =
                    getWidth()
                            - left
                            - 20;

            int chartHeight =
                    getHeight()
                            - top
                            - bottom;

            int count =
                    Math.min(
                            values.length,
                            15
                    );

            for (int i = 0;
                 i < count;
                 i++) {

                int barWidth =
                        Math.max(
                                12,
                                chartWidth / count - 5
                        );

                int barHeight =
                        (int)
                                (
                                        values[i]
                                                / 100.0
                                                * chartHeight
                                );

                int x =
                        left
                                + i
                                * (
                                chartWidth
                                        / count
                        );

                int y =
                        getHeight()
                                - bottom
                                - barHeight;

                g2.setColor(GREEN);

                g2.fillRoundRect(
                        x,
                        y,
                        barWidth,
                        barHeight,
                        8,
                        8
                );

                g2.setColor(MUTED);

                g2.setFont(
                        new Font(
                                "SansSerif",
                                Font.PLAIN,
                                8
                        )
                );

                g2.drawString(
                        String.valueOf(i + 1),
                        x,
                        getHeight() - 12
                );
            }

            g2.dispose();
        }
    }

    // =====================================================
    // GET DISTRIBUTION
    // =====================================================

    @SuppressWarnings("unchecked")
    private int[] getDistributionValues() {

        if (visualizationData == null) {

            return new int[]{
                    0,
                    0,
                    0,
                    0
            };
        }

        Map<String, Object> distribution =
                (Map<String, Object>)
                        visualizationData.get(
                                "performance_distribution"
                        );

        if (distribution == null) {

            return new int[]{
                    0,
                    0,
                    0,
                    0
            };
        }

        return new int[]{
                toInt(
                        distribution.get(
                                "Excellent"
                        )
                ),
                toInt(
                        distribution.get(
                                "Good"
                        )
                ),
                toInt(
                        distribution.get(
                                "Average"
                        )
                ),
                toInt(
                        distribution.get(
                                "At-Risk"
                        )
                )
        };
    }

    // =====================================================
    // GET PERFORMANCE VALUES
    // =====================================================

    private double[] getPerformanceValues() {

        if (visualizationData == null) {
            return new double[0];
        }

        return getDoubleArray(
                visualizationData.get(
                        "performance_values"
                )
        );
    }

    // =====================================================
    // GET ATTENDANCE VALUES
    // =====================================================

    private double[] getAttendanceValues() {

        if (visualizationData == null) {
            return new double[0];
        }

        return getDoubleArray(
                visualizationData.get(
                        "attendance_values"
                )
        );
    }

    // =====================================================
    // GET SCATTER VALUES
    // =====================================================

    private double[][] getScatterValues() {

        if (visualizationData == null) {
            return new double[0][0];
        }

        Object object =
                visualizationData.get(
                        "scatter_data"
                );

        if (!(object instanceof List)) {
            return new double[0][0];
        }

        List<?> list =
                (List<?>) object;

        double[][] result =
                new double[list.size()][];

        for (int i = 0;
             i < list.size();
             i++) {

            Object row =
                    list.get(i);

            if (!(row instanceof List)) {

                result[i] =
                        new double[0];

                continue;
            }

            List<?> rowList =
                    (List<?>) row;

            result[i] =
                    new double[rowList.size()];

            for (int j = 0;
                 j < rowList.size();
                 j++) {

                result[i][j] =
                        toDouble(
                                rowList.get(j)
                        );
            }
        }

        return result;
    }

    // =====================================================
    // DOUBLE ARRAY
    // =====================================================

    private double[] getDoubleArray(
            Object object
    ) {

        if (!(object instanceof List)) {

            return new double[0];
        }

        List<?> list =
                (List<?>) object;

        double[] result =
                new double[list.size()];

        for (int i = 0;
             i < list.size();
             i++) {

            result[i] =
                    toDouble(
                            list.get(i)
                    );
        }

        return result;
    }

    // =====================================================
    // NUMBER HELPERS
    // =====================================================

    private int toInt(Object value) {

        if (value instanceof Number) {

            return ((Number) value).intValue();
        }

        try {

            return Integer.parseInt(
                    String.valueOf(value)
            );

        } catch (Exception e) {

            return 0;
        }
    }

    private double toDouble(Object value) {

        if (value instanceof Number) {

            return ((Number) value).doubleValue();
        }

        try {

            return Double.parseDouble(
                    String.valueOf(value)
            );

        } catch (Exception e) {

            return 0;
        }
    }

    private String formatValue(
            Object value
    ) {

        if (value == null) {
            return "N/A";
        }

        if (value instanceof Number) {

            double number =
                    ((Number) value).doubleValue();

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
    // NO DATA
    // =====================================================

    private void drawNoData(
            Graphics2D g2
    ) {

        g2.setColor(MUTED);

        g2.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        String text =
                "No visualization data available";

        int width =
                g2.getFontMetrics()
                        .stringWidth(text);

        g2.drawString(
                text,
                getWidth() / 2 - width / 2,
                getHeight() / 2
        );
    }

    // =====================================================
    // BUTTON
    // =====================================================

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
                        new Color(
                                220,
                                223,
                                235
                        )
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =====================================================
    // ROUNDED PANEL
    // =====================================================

    private static class RoundedPanel
            extends JPanel {

        private final Color color;
        private final int radius;

        public RoundedPanel(
                Color color,
                int radius
        ) {

            this.color = color;
            this.radius = radius;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g
        ) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            g2.fill(
                    new RoundRectangle2D.Double(
                            0,
                            0,
                            getWidth() - 1,
                            getHeight() - 1,
                            radius,
                            radius
                    )
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =====================================================
    // MAIN
    // =====================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(() -> {

            Visualizations page =
                    new Visualizations();

            page.setVisible(true);
        });
    }
}