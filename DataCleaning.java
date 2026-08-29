package eduinsight;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

public class DataCleaning extends JFrame {

    // ================= COLORS =================

    private final Color BG =
            new Color(245, 247, 252);

    private final Color WHITE =
            Color.WHITE;

    private final Color PRIMARY =
            new Color(99, 91, 255);

    private final Color TEXT =
            new Color(25, 31, 45);

    private final Color MUTED =
            new Color(116, 124, 142);

    private final Color GREEN =
            new Color(34, 197, 140);

    private final Color RED =
            new Color(239, 68, 68);


    // ================= LABELS =================

    private JLabel datasetLabel;

    private JLabel rowsBeforeLabel;

    private JLabel rowsAfterLabel;

    private JLabel duplicatesLabel;

    private JLabel missingLabel;

    private JLabel statusLabel;


    // ================= CONSTRUCTOR =================

    public DataCleaning() {

        setTitle(
                "EduInsight | Data Cleaning"
        );

        setSize(
                1150,
                720
        );

        setMinimumSize(
                new Dimension(
                        1000,
                        650
                )
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        JPanel main =
                new JPanel(
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

        // NEW: actually pull in whatever dataset was
        // selected on the Upload Dataset page.
        loadDatasetInfo();
    }


    // ================= TOP BAR =================

    private JPanel createTopBar() {

        JPanel bar =
                new JPanel(
                        new BorderLayout()
                );

        bar.setBackground(WHITE);

        bar.setBorder(
                new EmptyBorder(
                        18,
                        28,
                        18,
                        28
                )
        );


        // LEFT

        JPanel left =
                new JPanel();

        left.setOpaque(false);

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel title =
                new JLabel(
                        "Data Cleaning"
                );

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Clean and prepare your dataset for analysis"
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


        // BACK BUTTON

        JButton backButton =
                new JButton(
                        "← Back to Dashboard"
                );

        styleOutlineButton(
                backButton
        );


        backButton.addActionListener(
                e -> {

                    dispose();

                    Dashboard dashboard =
                            new Dashboard();

                    dashboard.setVisible(true);
                }
        );


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


    // ================= CONTENT =================

    private JScrollPane createContent() {

        JPanel content =
                new JPanel();

        content.setBackground(BG);

        content.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );


        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );


        content.add(
                createDatasetCard()
        );


        content.add(
                Box.createVerticalStrut(20)
        );


        content.add(
                createStatisticsCards()
        );


        content.add(
                Box.createVerticalStrut(20)
        );


        content.add(
                createCleaningOptions()
        );


        content.add(
                Box.createVerticalStrut(20)
        );


        content.add(
                createStatusCard()
        );


        JScrollPane scroll =
                new JScrollPane(
                        content
                );

        scroll.setBorder(null);

        scroll.getViewport()
                .setBackground(BG);

        scroll.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );


        return scroll;
    }


    // ================= DATASET CARD =================

    private JPanel createDatasetCard() {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        90
                )
        );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(

                        new LineBorder(
                                new Color(
                                        225,
                                        228,
                                        238
                                )
                        ),

                        new EmptyBorder(
                                18,
                                22,
                                18,
                                22
                        )
                )
        );


        JLabel title =
                new JLabel(
                        "CURRENT DATASET"
                );

        title.setForeground(MUTED);

        title.setFont(
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


        JPanel left =
                new JPanel();

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

        left.add(datasetLabel);


        card.add(
                left,
                BorderLayout.WEST
        );


        return card;
    }


    // ================= STATISTICS CARDS =================

    private JPanel createStatisticsCards() {

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        panel.setOpaque(false);

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        110
                )
        );


        rowsBeforeLabel =
                new JLabel(
                        "0"
                );

        rowsAfterLabel =
                new JLabel(
                        "0"
                );

        duplicatesLabel =
                new JLabel(
                        "0"
                );

        missingLabel =
                new JLabel(
                        "0"
                );


        panel.add(
                createStatCard(
                        "ROWS BEFORE",
                        rowsBeforeLabel,
                        PRIMARY
                )
        );


        panel.add(
                createStatCard(
                        "ROWS AFTER",
                        rowsAfterLabel,
                        GREEN
                )
        );


        panel.add(
                createStatCard(
                        "DUPLICATES",
                        duplicatesLabel,
                        RED
                )
        );


        panel.add(
                createStatCard(
                        "MISSING VALUES",
                        missingLabel,
                        new Color(
                                245,
                                158,
                                11
                        )
                )
        );


        return panel;
    }


    // ================= STAT CARD =================

    private JPanel createStatCard(

            String title,
            JLabel value,
            Color color

    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(WHITE);

        card.setBorder(
                new EmptyBorder(
                        18,
                        18,
                        18,
                        18
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );

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


    // ================= CLEANING OPTIONS =================

    private JPanel createCleaningOptions() {

        JPanel card =
                new JPanel();

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        400
                )
        );

        card.setBackground(WHITE);

        card.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );


        JLabel title =
                new JLabel(
                        "Cleaning Operations"
                );

        title.setForeground(TEXT);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        17
                )
        );


        JLabel subtitle =
                new JLabel(
                        "Choose an operation to clean your dataset"
                );

        subtitle.setForeground(MUTED);

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );


        JPanel buttons =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                15,
                                15
                        )
                );

        buttons.setOpaque(false);

        buttons.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        180
                )
        );


        JButton removeDuplicates =
                createCleaningButton(
                        "Remove Duplicates",
                        "Remove duplicate rows"
                );


        JButton dropMissing =
                createCleaningButton(
                        "Drop Missing Rows",
                        "Remove rows with missing values"
                );


        JButton fillMean =
                createCleaningButton(
                        "Fill with Mean",
                        "Fill numeric missing values"
                );


        JButton fillUnknown =
                createCleaningButton(
                        "Fill Unknown",
                        "Replace text missing values"
                );


        JButton removeEmptyColumns =
                createCleaningButton(
                        "Remove Empty Columns",
                        "Delete completely empty columns"
                );


        JButton cleanAll =
                createCleaningButton(
                        "Clean Dataset",
                        "Run all recommended cleaning"
                );


        // ================= ACTIONS =================
        //
        // NOTE: these pass the exact keyword data_engine.py
        // expects (e.g. "remove_duplicates"), and now actually
        // call the engine instead of just faking success.

        removeDuplicates.addActionListener(
                e -> runCleaningAction(
                        "remove_duplicates",
                        "Remove Duplicates"
                )
        );


        dropMissing.addActionListener(
                e -> runCleaningAction(
                        "drop_missing",
                        "Drop Missing Rows"
                )
        );


        fillMean.addActionListener(
                e -> runCleaningAction(
                        "fill_mean",
                        "Fill Missing Values with Mean"
                )
        );


        fillUnknown.addActionListener(
                e -> runCleaningAction(
                        "fill_unknown",
                        "Fill Missing Values with Unknown"
                )
        );


        removeEmptyColumns.addActionListener(
                e -> runCleaningAction(
                        "remove_empty_columns",
                        "Remove Empty Columns"
                )
        );


        cleanAll.addActionListener(
                e -> runCleaningAction(
                        "clean_all",
                        "Clean Dataset"
                )
        );


        buttons.add(
                removeDuplicates
        );

        buttons.add(
                dropMissing
        );

        buttons.add(
                fillMean
        );

        buttons.add(
                fillUnknown
        );

        buttons.add(
                removeEmptyColumns
        );

        buttons.add(
                cleanAll
        );


        card.add(title);

        card.add(
                Box.createVerticalStrut(5)
        );

        card.add(subtitle);

        card.add(
                Box.createVerticalStrut(20)
        );

        card.add(buttons);


        return card;
    }


    // ================= CLEANING BUTTON =================

    private JButton createCleaningButton(

            String title,
            String tooltip

    ) {

        JButton button =
                new JButton(
                        "<html><center>"
                                + "<b>"
                                + title
                                + "</b>"
                                + "<br>"
                                + "<span style='font-size:9px'>"
                                + tooltip
                                + "</span>"
                                + "</center></html>"
                );


        button.setBackground(
                new Color(
                        248,
                        249,
                        255
                )
        );

        button.setForeground(TEXT);

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );


        button.setBorder(
                new LineBorder(
                        new Color(
                                225,
                                228,
                                238
                        )
                )
        );


        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );


        return button;
    }


    // ================= STATUS CARD =================

    private JPanel createStatusCard() {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        80
                )
        );

        card.setBackground(WHITE);

        card.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );


        JLabel title =
                new JLabel(
                        "STATUS"
                );

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
                        "Waiting for cleaning operation..."
                );

        statusLabel.setForeground(MUTED);

        statusLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );


        JPanel left =
                new JPanel();

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


        card.add(
                left,
                BorderLayout.WEST
        );


        return card;
    }


    // =====================================================
    // LOAD DATASET INFO  (NEW)
    // =====================================================
    //
    // Reads DatasetManager for the file chosen on the Upload
    // page, shows its name immediately, then calls the Python
    // engine in the background to fill in the row/duplicate/
    // missing-value stat cards.

    private void loadDatasetInfo() {

        if (!DatasetManager.hasDataset()) {

            datasetLabel.setText("No dataset selected");

            statusLabel.setText("Please upload a dataset first.");
            statusLabel.setForeground(MUTED);

            return;
        }

        String path = DatasetManager.getDatasetPath();

        datasetLabel.setText(new File(path).getName());

        statusLabel.setText("Loading dataset info...");
        statusLabel.setForeground(PRIMARY);

        SwingWorker<String, Void> worker = new SwingWorker<>() {

            @Override
            protected String doInBackground() throws Exception {
                return PythonEngine.run(path);
            }

            @Override
            protected void done() {

                try {

                    String json = get();
                    applyDatasetInfo(json);

                } catch (Exception e) {

                    statusLabel.setText("Failed to load dataset info");
                    statusLabel.setForeground(RED);

                    Throwable cause = e.getCause() != null ? e.getCause() : e;

                    JTextArea area = new JTextArea(String.valueOf(cause.getMessage()));
                    area.setEditable(false);
                    area.setLineWrap(true);
                    area.setWrapStyleWord(true);

                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new Dimension(500, 220));

                    JOptionPane.showMessageDialog(
                            DataCleaning.this,
                            scroll,
                            "Data Engine Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }

    private void applyDatasetInfo(String json) {

        if (json == null || json.trim().isEmpty()
                || json.contains("\"success\": false")
                || json.contains("\"success\":false")) {

            statusLabel.setText("Failed to load dataset info");
            statusLabel.setForeground(RED);

            return;
        }

        String rows = PythonEngine.extractJsonValue(json, "\"rows\":");
        String duplicates = PythonEngine.extractJsonValue(json, "\"duplicates\":");
        String missingTotal = PythonEngine.extractJsonValue(json, "\"missing_total\":");

        rowsBeforeLabel.setText(rows);
        rowsAfterLabel.setText(rows); // no cleaning run yet, so "after" == "before"
        duplicatesLabel.setText(duplicates);
        missingLabel.setText(missingTotal);

        statusLabel.setText("Dataset loaded successfully");
        statusLabel.setForeground(GREEN);
    }


    // =====================================================
    // RUN CLEANING  (NEW: actually calls the engine now)
    // =====================================================

    private void runCleaningAction(String actionKeyword, String actionLabel) {

        if (!DatasetManager.hasDataset()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please upload a dataset first.",
                    "No Dataset Selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        statusLabel.setText(actionLabel + " started...");
        statusLabel.setForeground(PRIMARY);

        String path = DatasetManager.getDatasetPath();

        SwingWorker<String, Void> worker = new SwingWorker<>() {

            @Override
            protected String doInBackground() throws Exception {
                return PythonEngine.run(path, actionKeyword);
            }

            @Override
            protected void done() {

                try {

                    String json = get();

                    if (json.contains("\"success\": false") || json.contains("\"success\":false")) {

                        statusLabel.setText(actionLabel + " failed");
                        statusLabel.setForeground(RED);

                        JOptionPane.showMessageDialog(
                                DataCleaning.this,
                                json,
                                "Data Engine Error",
                                JOptionPane.ERROR_MESSAGE
                        );

                        return;
                    }

                    String rowsAfter = PythonEngine.extractJsonValue(json, "\"rows_after\":");
                    String duplicatesAfter = PythonEngine.extractJsonValue(json, "\"duplicates_after\":");
                    String missingAfter = PythonEngine.extractJsonValue(json, "\"missing_after\":");

                    rowsAfterLabel.setText(rowsAfter);
                    duplicatesLabel.setText(duplicatesAfter);
                    missingLabel.setText(missingAfter);

                    statusLabel.setText(actionLabel + " completed successfully");
                    statusLabel.setForeground(GREEN);

                    JOptionPane.showMessageDialog(
                            DataCleaning.this,
                            actionLabel + " completed successfully.\n"
                                    + "Cleaned file saved alongside the original dataset.",
                            "EduInsight",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                } catch (Exception e) {

                    statusLabel.setText(actionLabel + " failed");
                    statusLabel.setForeground(RED);

                    Throwable cause = e.getCause() != null ? e.getCause() : e;

                    JTextArea area = new JTextArea(String.valueOf(cause.getMessage()));
                    area.setEditable(false);
                    area.setLineWrap(true);
                    area.setWrapStyleWord(true);

                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new Dimension(500, 220));

                    JOptionPane.showMessageDialog(
                            DataCleaning.this,
                            scroll,
                            "Data Engine Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
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


    // ================= MAIN =================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    DataCleaning page =
                            new DataCleaning();

                    page.setVisible(true);
                }
        );
    }
}