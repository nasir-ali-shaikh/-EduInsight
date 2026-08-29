package eduinsight;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class Dashboard extends JFrame {

    // ================= COLORS =================

    private final Color BG = new Color(245, 247, 252);
    private final Color SIDEBAR = new Color(18, 24, 38);
    private final Color SIDEBAR_LIGHT = new Color(30, 38, 56);

    private final Color PRIMARY = new Color(99, 91, 255);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT = new Color(25, 31, 45);
    private final Color MUTED = new Color(116, 124, 142);

    private final Color GREEN = new Color(34, 197, 140);
    private final Color ORANGE = new Color(245, 158, 11);
    private final Color RED = new Color(239, 68, 68);
    private final Color BLUE = new Color(59, 130, 246);

    private JPanel contentPanel;
    private JLabel pageTitle;

    // ================= CONSTRUCTOR =================

    public Dashboard() {

        setTitle("EduInsight | Student Analytics");
        setSize(1350, 800);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);

        root.add(createSidebar(), BorderLayout.WEST);
        root.add(createMainArea(), BorderLayout.CENTER);

        setContentPane(root);
    }

    // =====================================================
    // SIDEBAR
    // =====================================================

    private JPanel createSidebar() {

        JPanel sidebar = new JPanel(new BorderLayout());

        sidebar.setPreferredSize(new Dimension(245, 0));
        sidebar.setBackground(SIDEBAR);
        sidebar.setBorder(new EmptyBorder(25, 15, 20, 15));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        // LOGO

        JPanel logoPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 8, 0)
        );

        logoPanel.setOpaque(false);

        JLabel logoIcon = new JLabel("E");

        logoIcon.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        logoIcon.setPreferredSize(
                new Dimension(38, 38)
        );

        logoIcon.setOpaque(true);
        logoIcon.setBackground(PRIMARY);
        logoIcon.setForeground(Color.WHITE);

        logoIcon.setFont(
                new Font("SansSerif", Font.BOLD, 20)
        );

        JLabel logoText = new JLabel("EduInsight");

        logoText.setForeground(Color.WHITE);

        logoText.setFont(
                new Font("SansSerif", Font.BOLD, 22)
        );

        logoPanel.add(logoIcon);
        logoPanel.add(logoText);

        JLabel subtitle = new JLabel(
                "Student Analytics Platform"
        );

        subtitle.setForeground(
                new Color(145, 154, 174)
        );

        subtitle.setFont(
                new Font("SansSerif", Font.PLAIN, 11)
        );

        subtitle.setBorder(
                new EmptyBorder(6, 46, 0, 0)
        );

        top.add(logoPanel);
        top.add(subtitle);

        top.add(Box.createVerticalStrut(35));

        // MENU

        JPanel menu = new JPanel();

        menu.setOpaque(false);

        menu.setLayout(
                new BoxLayout(
                        menu,
                        BoxLayout.Y_AXIS
                )
        );

        addMenu(menu, "⌂", "Dashboard", true,
                e -> showDashboard());

        addMenu(menu, "↑", "Upload Dataset", false,
                e -> uploadDataset());

        addMenu(menu, "✦", "Data Cleaning", false,
                e -> openDataCleaning());

        addMenu(menu, "▦", "Analytics", false,
                e -> openAnalytics());

        addMenu(menu, "◉", "Visualizations", false,
                e -> openVisualizations());

        addMenu(menu, "!", "At-Risk Students", false,
                e -> openAtRiskStudents());

        addMenu(menu, "▤", "Reports", false,
                e -> openReports());

        addMenu(menu, "⚙", "Settings", false,
                e -> openSettings());

        top.add(menu);

        sidebar.add(top, BorderLayout.NORTH);

        // BOTTOM

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);

        bottom.setLayout(
                new BoxLayout(
                        bottom,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel help = new JLabel("Need help?");

        help.setForeground(
                new Color(150, 158, 178)
        );

        help.setFont(
                new Font("SansSerif", Font.PLAIN, 11)
        );

        JLabel version = new JLabel("EduInsight v1.0");

        version.setForeground(
                new Color(105, 114, 133)
        );

        version.setFont(
                new Font("SansSerif", Font.PLAIN, 10)
        );

        bottom.add(help);
        bottom.add(Box.createVerticalStrut(5));
        bottom.add(version);

        sidebar.add(bottom, BorderLayout.SOUTH);

        return sidebar;
    }

    // =====================================================
    // MENU ITEM
    // =====================================================

    private void addMenu(
            JPanel menu,
            String icon,
            String text,
            boolean selected,
            ActionListener action
    ) {

        JPanel item = new JPanel(new BorderLayout());

        item.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 46)
        );

        item.setBackground(
                selected ? SIDEBAR_LIGHT : SIDEBAR
        );

        item.setBorder(
                new EmptyBorder(0, 10, 0, 8)
        );

        JLabel iconLabel = new JLabel(icon);

        iconLabel.setPreferredSize(
                new Dimension(32, 32)
        );

        iconLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        iconLabel.setForeground(
                selected
                        ? Color.WHITE
                        : new Color(145, 154, 174)
        );

        iconLabel.setFont(
                new Font("SansSerif", Font.BOLD, 16)
        );

        JLabel label = new JLabel(text);

        label.setForeground(
                selected
                        ? Color.WHITE
                        : new Color(175, 183, 200)
        );

        label.setFont(
                new Font(
                        "SansSerif",
                        selected ? Font.BOLD : Font.PLAIN,
                        13
                )
        );

        item.add(iconLabel, BorderLayout.WEST);
        item.add(label, BorderLayout.CENTER);

        item.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        item.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(MouseEvent e) {

                        if (!selected) {
                            item.setBackground(SIDEBAR_LIGHT);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                        if (!selected) {
                            item.setBackground(SIDEBAR);
                        }
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        action.actionPerformed(
                                new ActionEvent(
                                        item,
                                        ActionEvent.ACTION_PERFORMED,
                                        text
                                )
                        );
                    }
                }
        );

        menu.add(item);
        menu.add(Box.createVerticalStrut(5));
    }

    // =====================================================
    // MAIN AREA
    // =====================================================

    private JPanel createMainArea() {

        JPanel main = new JPanel(
                new BorderLayout()
        );

        main.setBackground(BG);

        main.add(
                createTopBar(),
                BorderLayout.NORTH
        );

        contentPanel = new JPanel(
                new BorderLayout()
        );

        contentPanel.setBackground(BG);

        contentPanel.setBorder(
                new EmptyBorder(25, 28, 25, 28)
        );

        main.add(
                contentPanel,
                BorderLayout.CENTER
        );

        showDashboard();

        return main;
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
                new EmptyBorder(15, 28, 15, 28)
        );

        JPanel left = new JPanel();
        left.setOpaque(false);

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        pageTitle = new JLabel("Dashboard");

        pageTitle.setForeground(TEXT);

        pageTitle.setFont(
                new Font("SansSerif", Font.BOLD, 22)
        );

        JLabel small = new JLabel(
                "Student Performance Overview"
        );

        small.setForeground(MUTED);

        small.setFont(
                new Font("SansSerif", Font.PLAIN, 11)
        );

        left.add(pageTitle);
        left.add(Box.createVerticalStrut(3));
        left.add(small);

        JPanel right = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        12,
                        2
                )
        );

        right.setOpaque(false);

        JTextField search = new JTextField(
                " Search students..."
        );

        search.setPreferredSize(
                new Dimension(190, 34)
        );

        search.setForeground(MUTED);

        search.setFont(
                new Font("SansSerif", Font.PLAIN, 11)
        );

        search.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(225, 228, 235)
                        ),
                        new EmptyBorder(0, 8, 0, 8)
                )
        );

        JButton notification = new JButton("🔔");

        notification.setPreferredSize(
                new Dimension(38, 34)
        );

        notification.setFocusPainted(false);
        notification.setBorderPainted(false);
        notification.setBackground(BG);

        JLabel avatar = new JLabel("A");

        avatar.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        avatar.setPreferredSize(
                new Dimension(34, 34)
        );

        avatar.setOpaque(true);
        avatar.setBackground(PRIMARY);
        avatar.setForeground(Color.WHITE);

        avatar.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        JLabel admin = new JLabel(
                "<html><b>Admin</b><br>"
                        + "<font color='#888888'>Administrator</font></html>"
        );

        admin.setFont(
                new Font("SansSerif", Font.PLAIN, 11)
        );

        right.add(search);
        right.add(notification);
        right.add(avatar);
        right.add(admin);

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);

        return bar;
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    private void showDashboard() {

        pageTitle.setText("Dashboard");

        contentPanel.removeAll();

        JPanel dashboard = new JPanel();

        dashboard.setBackground(BG);

        dashboard.setLayout(
                new BoxLayout(
                        dashboard,
                        BoxLayout.Y_AXIS
                )
        );

        // ================= WELCOME =================

        JPanel welcome = new JPanel(
                new BorderLayout()
        );

        welcome.setOpaque(false);

        welcome.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        80
                )
        );

        JPanel text = new JPanel();
        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel heading = new JLabel(
                "Welcome back, Admin 👋"
        );

        heading.setForeground(TEXT);

        heading.setFont(
                new Font("SansSerif", Font.BOLD, 24)
        );

        JLabel description = new JLabel(
                "Monitor and analyze student performance from one central platform."
        );

        description.setForeground(MUTED);

        description.setFont(
                new Font("SansSerif", Font.PLAIN, 13)
        );

        text.add(heading);
        text.add(Box.createVerticalStrut(5));
        text.add(description);

        JButton upload = new JButton(
                "+  Upload Dataset"
        );

        stylePrimary(upload);

        upload.addActionListener(
                e -> uploadDataset()
        );

        welcome.add(text, BorderLayout.WEST);
        welcome.add(upload, BorderLayout.EAST);

        dashboard.add(welcome);

        dashboard.add(
                Box.createVerticalStrut(18)
        );

        // ================= KPI CARDS =================

        JPanel cards = new JPanel(
                new GridLayout(1, 4, 16, 0)
        );

        cards.setOpaque(false);

        cards.add(
                createKpi(
                        "TOTAL STUDENTS",
                        "500",
                        "+12.5%",
                        "vs last month",
                        BLUE,
                        "●"
                )
        );

        cards.add(
                createKpi(
                        "AVERAGE MARKS",
                        "78.4%",
                        "+5.2%",
                        "vs last semester",
                        PRIMARY,
                        "★"
                )
        );

        cards.add(
                createKpi(
                        "ATTENDANCE",
                        "86.2%",
                        "+3.8%",
                        "overall average",
                        GREEN,
                        "✓"
                )
        );

        cards.add(
                createKpi(
                        "AT-RISK STUDENTS",
                        "32",
                        "-8.4%",
                        "needs attention",
                        RED,
                        "!"
                )
        );

        dashboard.add(cards);

        dashboard.add(
                Box.createVerticalStrut(18)
        );

        // ================= CHARTS =================

        JPanel charts = new JPanel(
                new GridLayout(1, 2, 16, 0)
        );

        charts.setOpaque(false);

        charts.add(createPerformancePanel());
        charts.add(createAttendancePanel());

        dashboard.add(charts);

        dashboard.add(
                Box.createVerticalStrut(18)
        );

        // ================= BOTTOM =================

        JPanel bottom = new JPanel(
                new GridLayout(1, 2, 16, 0)
        );

        bottom.setOpaque(false);

        bottom.add(
                createPerformanceSummaryPanel()
        );

        bottom.add(
                createActivityPanel()
        );

        dashboard.add(bottom);

        JScrollPane scroll = new JScrollPane(dashboard);

        scroll.setBorder(null);

        scroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        scroll.getViewport()
                .setBackground(BG);

        contentPanel.add(
                scroll,
                BorderLayout.CENTER
        );

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // =====================================================
    // KPI CARD
    // =====================================================

    private JPanel createKpi(
            String title,
            String value,
            String change,
            String description,
            Color color,
            String icon
    ) {

        JPanel card = new RoundedPanel(
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

        JLabel iconLabel = new JLabel(icon);

        iconLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        iconLabel.setPreferredSize(
                new Dimension(42, 42)
        );

        iconLabel.setOpaque(true);

        iconLabel.setBackground(
                new Color(
                        color.getRed(),
                        color.getGreen(),
                        color.getBlue(),
                        30
                )
        );

        iconLabel.setForeground(color);

        iconLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        17
                )
        );

        JPanel info = new JPanel();

        info.setOpaque(false);

        info.setLayout(
                new BoxLayout(
                        info,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel = new JLabel(title);

        titleLabel.setForeground(MUTED);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        JLabel valueLabel = new JLabel(value);

        valueLabel.setForeground(TEXT);

        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        25
                )
        );

        JPanel changePanel = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        3,
                        0
                )
        );

        changePanel.setOpaque(false);

        JLabel changeLabel = new JLabel(change);

        changeLabel.setForeground(
                color == RED ? RED : GREEN
        );

        changeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        10
                )
        );

        JLabel descLabel = new JLabel(description);

        descLabel.setForeground(MUTED);

        descLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        10
                )
        );

        changePanel.add(changeLabel);
        changePanel.add(descLabel);

        info.add(titleLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(valueLabel);
        info.add(Box.createVerticalStrut(3));
        info.add(changePanel);

        card.add(iconLabel, BorderLayout.WEST);
        card.add(info, BorderLayout.CENTER);

        return card;
    }

    // =====================================================
    // PERFORMANCE PANEL
    // =====================================================

    private JPanel createPerformancePanel() {

        JPanel panel = new RoundedPanel(
                WHITE,
                18
        );

        panel.setLayout(
                new BorderLayout()
        );

        panel.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        15,
                        20
                )
        );

        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setOpaque(false);

        JLabel title = new JLabel(
                "Performance Overview"
        );

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        JLabel period = new JLabel(
                "Last 6 Semesters"
        );

        period.setForeground(MUTED);

        period.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        10
                )
        );

        header.add(title, BorderLayout.WEST);
        header.add(period, BorderLayout.EAST);

        panel.add(header, BorderLayout.NORTH);

        panel.add(
                new PerformanceChart(),
                BorderLayout.CENTER
        );

        return panel;
    }

    // =====================================================
    // ATTENDANCE PANEL
    // =====================================================

    private JPanel createAttendancePanel() {

        JPanel panel = new RoundedPanel(
                WHITE,
                18
        );

        panel.setLayout(
                new BorderLayout()
        );

        panel.setBorder(
                new EmptyBorder(
                        18,
                        20,
                        15,
                        20
                )
        );

        JPanel header = new JPanel(
                new BorderLayout()
        );

        header.setOpaque(false);

        JLabel title = new JLabel(
                "Attendance Overview"
        );

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        JLabel status = new JLabel("Good");

        status.setForeground(GREEN);

        status.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        header.add(title, BorderLayout.WEST);
        header.add(status, BorderLayout.EAST);

        panel.add(header, BorderLayout.NORTH);

        panel.add(
                new AttendanceChart(),
                BorderLayout.CENTER
        );

        return panel;
    }

    // =====================================================
    // STUDENT PERFORMANCE SUMMARY
    // =====================================================

    private JPanel createPerformanceSummaryPanel() {

        JPanel panel = new RoundedPanel(
                WHITE,
                18
        );

        panel.setLayout(
                new BorderLayout()
        );

        panel.setBorder(
                new EmptyBorder(
                        17,
                        20,
                        17,
                        20
                )
        );

        JLabel title = new JLabel(
                "Student Performance Summary"
        );

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        panel.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();

        list.setOpaque(false);

        list.setLayout(
                new GridLayout(
                        4,
                        1,
                        0,
                        6
                )
        );

        list.add(
                createSummaryRow(
                        "Excellent",
                        "120 Students",
                        GREEN
                )
        );

        list.add(
                createSummaryRow(
                        "Good",
                        "230 Students",
                        BLUE
                )
        );

        list.add(
                createSummaryRow(
                        "Average",
                        "118 Students",
                        ORANGE
                )
        );

        list.add(
                createSummaryRow(
                        "At-Risk",
                        "32 Students",
                        RED
                )
        );

        panel.add(list, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSummaryRow(
            String category,
            String value,
            Color color
    ) {

        JPanel row = new JPanel(
                new BorderLayout()
        );

        row.setOpaque(false);

        JLabel left = new JLabel(
                "●  " + category
        );

        left.setForeground(color);

        left.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );

        JLabel right = new JLabel(value);

        right.setForeground(TEXT);

        right.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        row.add(left, BorderLayout.WEST);
        row.add(right, BorderLayout.EAST);

        return row;
    }

    // =====================================================
    // RECENT ACTIVITY
    // =====================================================

    private JPanel createActivityPanel() {

        JPanel panel = new RoundedPanel(
                WHITE,
                18
        );

        panel.setLayout(
                new BorderLayout()
        );

        panel.setBorder(
                new EmptyBorder(
                        17,
                        20,
                        15,
                        20
                )
        );

        JLabel title = new JLabel(
                "Recent Activity"
        );

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        panel.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();

        list.setOpaque(false);

        list.setLayout(
                new BoxLayout(
                        list,
                        BoxLayout.Y_AXIS
                )
        );

        addActivity(
                list,
                "Dataset uploaded",
                "Student_Data.xlsx",
                "2 min ago",
                BLUE
        );

        addActivity(
                list,
                "Data cleaning completed",
                "500 records processed",
                "15 min ago",
                GREEN
        );

        addActivity(
                list,
                "Risk analysis completed",
                "32 students identified",
                "1 hour ago",
                ORANGE
        );

        panel.add(list, BorderLayout.CENTER);

        return panel;
    }

    private void addActivity(
            JPanel list,
            String title,
            String detail,
            String time,
            Color color
    ) {

        JPanel row = new JPanel(
                new BorderLayout()
        );

        row.setOpaque(false);

        row.setBorder(
                new EmptyBorder(
                        8,
                        0,
                        8,
                        0
                )
        );

        JLabel dot = new JLabel("●");

        dot.setForeground(color);

        dot.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        0,
                        10
                )
        );

        JPanel text = new JPanel();

        text.setOpaque(false);

        text.setLayout(
                new BoxLayout(
                        text,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel titleLabel = new JLabel(title);

        titleLabel.setForeground(TEXT);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        JLabel detailLabel = new JLabel(detail);

        detailLabel.setForeground(MUTED);

        detailLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        10
                )
        );

        text.add(titleLabel);
        text.add(detailLabel);

        JLabel timeLabel = new JLabel(time);

        timeLabel.setForeground(MUTED);

        timeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        9
                )
        );

        row.add(dot, BorderLayout.WEST);
        row.add(text, BorderLayout.CENTER);
        row.add(timeLabel, BorderLayout.EAST);

        list.add(row);
    }

    // =====================================================
    // UPLOAD DATASET
    // =====================================================

    private void uploadDataset() {

        UploadDataset uploadDataset =
                new UploadDataset();

        uploadDataset.setVisible(true);

        dispose();
    }


    // =====================================================
    // OPEN DATA CLEANING
    // =====================================================

    private void openDataCleaning() {

        if (!DatasetManager.hasDataset()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please upload a dataset first.",
                    "No Dataset Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        DataCleaning dataCleaning =
                new DataCleaning();

        dataCleaning.setVisible(true);

        dispose();
    }


    // =====================================================
    // OPEN ANALYTICS
    // =====================================================

    private void openAnalytics() {

        if (!DatasetManager.hasDataset()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please upload a dataset first.",
                    "No Dataset Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Analytics analytics =
                new Analytics();

        analytics.setVisible(true);

        dispose();
    }


    // =====================================================
    // OPEN VISUALIZATIONS
    // =====================================================

    private void openVisualizations() {

        if (!DatasetManager.hasDataset()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please upload a dataset first.",
                    "No Dataset Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Visualizations visualizations =
                new Visualizations();

        visualizations.setVisible(true);

        dispose();
    }


    // =====================================================
    // OPEN AT-RISK STUDENTS
    // =====================================================

    private void openAtRiskStudents() {

        if (!DatasetManager.hasDataset()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please upload a dataset first.",
                    "No Dataset Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        AtRiskStudents atRiskStudents =
                new AtRiskStudents();

        atRiskStudents.setVisible(true);

        dispose();
    }


    // =====================================================
    // OPEN REPORTS
    // =====================================================

    private void openReports() {

        if (!DatasetManager.hasDataset()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please upload a dataset first.",
                    "No Dataset Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Reports reports =
                new Reports();

        reports.setVisible(true);

        dispose();
    }


    // =====================================================
    // OPEN SETTINGS
    // =====================================================

    private void openSettings() {

        // Settings doesn't require a dataset — it can also
        // be used to upload one or clear the current one.

        Settings settings =
                new Settings();

        settings.setVisible(true);

        dispose();
    }


    // =====================================================
    // BUTTON STYLE
    // =====================================================

    private void stylePrimary(
            JButton button
    ) {

        button.setBackground(PRIMARY);
        button.setForeground(Color.WHITE);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setBorder(
                new EmptyBorder(
                        11,
                        18,
                        11,
                        18
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =====================================================
    // MESSAGE
    // =====================================================

    private void showMessage(
            String title,
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
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
    // PERFORMANCE CHART
    // =====================================================

    private class PerformanceChart
            extends JPanel {

        PerformanceChart() {

            setOpaque(false);

            setPreferredSize(
                    new Dimension(
                            450,
                            230
                    )
            );
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

            int width = getWidth();
            int height = getHeight();

            int left = 45;
            int right = 20;
            int top = 20;
            int bottom = 35;

            g2.setStroke(
                    new BasicStroke(1)
            );

            g2.setColor(
                    new Color(
                            235,
                            237,
                            243
                    )
            );

            for (int i = 0; i <= 4; i++) {

                int y =
                        top
                                + i
                                * (
                                height
                                        - top
                                        - bottom
                        )
                                / 4;

                g2.drawLine(
                        left,
                        y,
                        width - right,
                        y
                );
            }

            int[] values =
                    {64, 71, 68, 79, 76, 88};

            String[] labels =
                    {"S1", "S2", "S3", "S4", "S5", "S6"};

            int chartWidth =
                    width - left - right;

            int chartHeight =
                    height - top - bottom;

            int[] xPoints =
                    new int[values.length];

            int[] yPoints =
                    new int[values.length];

            for (int i = 0;
                 i < values.length;
                 i++) {

                xPoints[i] =
                        left
                                + i
                                * chartWidth
                                / (
                                values.length - 1
                        );

                yPoints[i] =
                        top
                                + chartHeight
                                - (
                                values[i]
                                        * chartHeight
                                        / 100
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
                        xPoints[i],
                        yPoints[i],
                        xPoints[i + 1],
                        yPoints[i + 1]
                );
            }

            for (int i = 0;
                 i < values.length;
                 i++) {

                g2.setColor(WHITE);

                g2.fillOval(
                        xPoints[i] - 5,
                        yPoints[i] - 5,
                        10,
                        10
                );

                g2.setColor(PRIMARY);

                g2.fillOval(
                        xPoints[i] - 3,
                        yPoints[i] - 3,
                        6,
                        6
                );

                g2.setColor(MUTED);

                g2.setFont(
                        new Font(
                                "SansSerif",
                                Font.PLAIN,
                                9
                        )
                );

                g2.drawString(
                        labels[i],
                        xPoints[i] - 7,
                        height - 10
                );
            }

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

            setPreferredSize(
                    new Dimension(
                            450,
                            230
                    )
            );
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

            int size =
                    Math.min(
                            getWidth(),
                            getHeight()
                    );

            int diameter =
                    Math.min(
                            145,
                            size - 35
                    );

            int x =
                    getWidth() / 2
                            - diameter / 2;

            int y =
                    getHeight() / 2
                            - diameter / 2;

            g2.setStroke(
                    new BasicStroke(
                            18,
                            BasicStroke.CAP_ROUND,
                            BasicStroke.JOIN_ROUND
                    )
            );

            // BACKGROUND RING

            g2.setColor(
                    new Color(
                            232,
                            234,
                            241
                    )
            );

            g2.drawArc(
                    x,
                    y,
                    diameter,
                    diameter,
                    0,
                    360
            );

            // ATTENDANCE VALUE

            g2.setColor(GREEN);

            g2.drawArc(
                    x,
                    y,
                    diameter,
                    diameter,
                    90,
                    -310
            );

            // VALUE TEXT

            g2.setColor(TEXT);

            g2.setFont(
                    new Font(
                            "SansSerif",
                            Font.BOLD,
                            24
                    )
            );

            String value = "86.2%";

            int valueWidth =
                    g2.getFontMetrics()
                            .stringWidth(value);

            g2.drawString(
                    value,
                    getWidth() / 2
                            - valueWidth / 2,
                    getHeight() / 2
                            + 5
            );

            // SUBTEXT

            g2.setFont(
                    new Font(
                            "SansSerif",
                            Font.PLAIN,
                            10
                    )
            );

            g2.setColor(MUTED);

            String text =
                    "Average Attendance";

            int textWidth =
                    g2.getFontMetrics()
                            .stringWidth(text);

            g2.drawString(
                    text,
                    getWidth() / 2
                            - textWidth / 2,
                    getHeight() / 2
                            + 23
            );

            g2.dispose();
        }
    }
}