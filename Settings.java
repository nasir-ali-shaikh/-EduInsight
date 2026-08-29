package eduinsight;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;

public class Settings extends JFrame {

    // ================= COLORS =================

    private final Color BG = new Color(245, 247, 252);
    private final Color WHITE = Color.WHITE;
    private final Color PRIMARY = new Color(99, 91, 255);
    private final Color TEXT = new Color(25, 31, 45);
    private final Color MUTED = new Color(116, 124, 142);
    private final Color RED = new Color(239, 68, 68);
    private final Color GREEN = new Color(34, 197, 140);

    // ================= LABELS =================

    private JLabel datasetPathLabel;
    private JLabel statusLabel;

    // ================= CONSTRUCTOR =================

    public Settings() {

        setTitle("EduInsight | Settings");

        setSize(950, 700);
        setMinimumSize(new Dimension(800, 550));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG);

        main.add(createTopBar(), BorderLayout.NORTH);
        main.add(createContent(), BorderLayout.CENTER);

        setContentPane(main);

        refreshDatasetInfo();
    }

    // ================= TOP BAR =================

    private JPanel createTopBar() {

        JPanel bar = new JPanel(new BorderLayout());

        bar.setBackground(WHITE);
        bar.setBorder(new EmptyBorder(18, 28, 18, 28));

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Settings");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Manage your dataset and app preferences");
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

        content.add(createDatasetSection());
        content.add(Box.createVerticalStrut(20));
        content.add(createAboutSection());
        content.add(Box.createVerticalStrut(20));
        content.add(createStatusCard());

        JScrollPane scroll = new JScrollPane(content);

        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        return scroll;
    }

    // ================= DATASET SECTION =================

    private JPanel createDatasetSection() {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(225, 228, 238)),
                        new EmptyBorder(20, 22, 20, 22)
                )
        );

        JLabel title = new JLabel("Dataset Management");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 15));

        JLabel pathHeading = new JLabel("CURRENT DATASET PATH");
        pathHeading.setForeground(MUTED);
        pathHeading.setFont(new Font("SansSerif", Font.BOLD, 10));

        datasetPathLabel = new JLabel("No dataset selected");
        datasetPathLabel.setForeground(TEXT);
        datasetPathLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        top.add(title);
        top.add(Box.createVerticalStrut(14));
        top.add(pathHeading);
        top.add(Box.createVerticalStrut(4));
        top.add(datasetPathLabel);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttons.setOpaque(false);

        JButton uploadNewButton = new JButton("Upload New Dataset");
        stylePrimaryButton(uploadNewButton);
        uploadNewButton.addActionListener(e -> {
            dispose();
            UploadDataset uploadDataset = new UploadDataset();
            uploadDataset.setVisible(true);
        });

        JButton clearButton = new JButton("Clear Dataset");
        styleDangerButton(clearButton);
        clearButton.addActionListener(e -> clearDataset());

        buttons.add(uploadNewButton);
        buttons.add(clearButton);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(top, BorderLayout.NORTH);
        wrapper.add(buttons, BorderLayout.SOUTH);

        card.add(wrapper, BorderLayout.CENTER);

        return card;
    }

    // ================= ABOUT SECTION =================

    private JPanel createAboutSection() {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(225, 228, 238)),
                        new EmptyBorder(20, 22, 20, 22)
                )
        );

        JLabel title = new JLabel("About EduInsight");
        title.setForeground(TEXT);
        title.setFont(new Font("SansSerif", Font.BOLD, 15));

        JTextArea about = new JTextArea(
                "EduInsight is a student performance & attendance analyzer.\n"
                        + "It reads CSV/Excel datasets, cleans them, and generates\n"
                        + "analytics, visualizations, and reports using a Python\n"
                        + "data engine connected to this Java desktop application."
        );

        about.setEditable(false);
        about.setOpaque(false);
        about.setLineWrap(true);
        about.setWrapStyleWord(true);
        about.setFont(new Font("SansSerif", Font.PLAIN, 12));
        about.setForeground(MUTED);

        JLabel version = new JLabel("Version 1.0");
        version.setForeground(MUTED);
        version.setFont(new Font("SansSerif", Font.BOLD, 11));
        version.setBorder(new EmptyBorder(14, 0, 0, 0));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        top.add(title);
        top.add(Box.createVerticalStrut(10));
        top.add(about);
        top.add(version);

        card.add(top, BorderLayout.CENTER);

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

        statusLabel = new JLabel("Ready");
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

    // ================= DATASET ACTIONS =================

    private void refreshDatasetInfo() {

        if (!DatasetManager.hasDataset()) {

            datasetPathLabel.setText("No dataset selected");

            statusLabel.setText("No dataset selected");
            statusLabel.setForeground(MUTED);

            return;
        }

        String path = DatasetManager.getDatasetPath();

        datasetPathLabel.setText(path);

        statusLabel.setText("Current dataset: " + new File(path).getName());
        statusLabel.setForeground(GREEN);
    }

    private void clearDataset() {

        if (!DatasetManager.hasDataset()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No dataset is currently selected.",
                    "Nothing to Clear",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "This will clear the currently selected dataset from EduInsight.\n"
                        + "(The file itself will not be deleted from your computer.)\n\n"
                        + "Continue?",
                "Clear Dataset",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        DatasetManager.setDatasetPath(null);

        refreshDatasetInfo();

        statusLabel.setText("Dataset cleared");
        statusLabel.setForeground(RED);
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

    private void styleDangerButton(JButton button) {

        button.setBackground(WHITE);
        button.setForeground(RED);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBorder(new LineBorder(new Color(250, 200, 200)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
            Settings page = new Settings();
            page.setVisible(true);
        });
    }
}