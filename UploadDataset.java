package eduinsight;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class UploadDataset extends JFrame {

    // ================= COLORS =================

    private final Color BG = new Color(245, 247, 252);
    private final Color WHITE = Color.WHITE;
    private final Color PRIMARY = new Color(99, 91, 255);
    private final Color TEXT = new Color(25, 31, 45);
    private final Color MUTED = new Color(116, 124, 142);
    private final Color GREEN = new Color(34, 197, 140);

    // ================= LABELS =================

    private JLabel fileNameLabel;
    private JLabel filePathLabel;
    private JLabel fileSizeLabel;
    private JLabel statusLabel;

    private JLabel rowsLabel;
    private JLabel columnsLabel;

    // ================= TABLE =================

    private JTable previewTable;
    private DefaultTableModel tableModel;

    // ================= CONSTRUCTOR =================

    public UploadDataset() {

        setTitle("EduInsight | Upload Dataset");

        setSize(1150, 720);

        setMinimumSize(new Dimension(1000, 650));

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel main = new JPanel(new BorderLayout());

        main.setBackground(BG);

        main.add(createTopBar(), BorderLayout.NORTH);

        main.add(createContent(), BorderLayout.CENTER);

        setContentPane(main);
    }

    // ================= TOP BAR =================

    private JPanel createTopBar() {

        JPanel bar = new JPanel(new BorderLayout());

        bar.setBackground(WHITE);

        bar.setBorder(new EmptyBorder(18, 28, 18, 28));

        JPanel left = new JPanel();

        left.setOpaque(false);

        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Upload Dataset");

        title.setForeground(TEXT);

        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Upload and preview your student dataset");

        subtitle.setForeground(MUTED);

        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));

        left.add(title);

        left.add(Box.createVerticalStrut(4));

        left.add(subtitle);

        JButton backButton = new JButton("← Back to Dashboard");

        backButton.setFocusPainted(false);

        backButton.setForeground(PRIMARY);

        backButton.setBackground(WHITE);

        backButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        backButton.setBorder(new EmptyBorder(10, 15, 10, 15));

        backButton.addActionListener(e -> {

            dispose();

            Dashboard dashboard = new Dashboard();

            dashboard.setVisible(true);
        });

        bar.add(left, BorderLayout.WEST);

        bar.add(backButton, BorderLayout.EAST);

        return bar;
    }

    // ================= MAIN CONTENT =================

    private JScrollPane createContent() {

        JPanel content = new JPanel();

        content.setBackground(BG);

        content.setBorder(new EmptyBorder(25, 30, 25, 30));

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.add(createUploadCard());

        content.add(Box.createVerticalStrut(20));

        content.add(createFileInfoCard());

        content.add(Box.createVerticalStrut(20));

        content.add(createPreviewCard());

        JScrollPane scroll = new JScrollPane(content);

        scroll.setBorder(null);

        scroll.getViewport().setBackground(BG);

        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        return scroll;
    }

    // ================= UPLOAD CARD =================

    private JPanel createUploadCard() {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 210));

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(225, 228, 238)),
                        new EmptyBorder(30, 30, 30, 30)
                )
        );

        JPanel center = new JPanel();

        center.setOpaque(false);

        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("↑");

        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        icon.setHorizontalAlignment(SwingConstants.CENTER);

        icon.setPreferredSize(new Dimension(55, 55));

        icon.setMaximumSize(new Dimension(55, 55));

        icon.setOpaque(true);

        icon.setBackground(new Color(235, 234, 255));

        icon.setForeground(PRIMARY);

        icon.setFont(new Font("SansSerif", Font.BOLD, 25));

        JLabel heading = new JLabel("Upload Student Dataset");

        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        heading.setForeground(TEXT);

        heading.setFont(new Font("SansSerif", Font.BOLD, 17));

        JLabel description = new JLabel("Select a CSV or Excel file to begin analysis");

        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        description.setForeground(MUTED);

        description.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JButton selectButton = new JButton("Select Dataset");

        stylePrimaryButton(selectButton);

        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        selectButton.addActionListener(e -> chooseFile());

        center.add(icon);

        center.add(Box.createVerticalStrut(10));

        center.add(heading);

        center.add(Box.createVerticalStrut(5));

        center.add(description);

        center.add(Box.createVerticalStrut(15));

        center.add(selectButton);

        card.add(center, BorderLayout.CENTER);

        return card;
    }

    // ================= FILE INFORMATION =================

    private JPanel createFileInfoCard() {

        JPanel card = new JPanel(new GridLayout(2, 2, 15, 15));

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        card.setOpaque(false);

        fileNameLabel = new JLabel("No file selected");

        filePathLabel = new JLabel("-");

        fileSizeLabel = new JLabel("-");

        statusLabel = new JLabel("Waiting for upload");

        card.add(createInfoBox("FILE NAME", fileNameLabel, PRIMARY));

        card.add(createInfoBox("FILE SIZE", fileSizeLabel, BLUE()));

        card.add(createInfoBox("FILE PATH", filePathLabel, MUTED));

        card.add(createInfoBox("STATUS", statusLabel, ORANGE()));

        return card;
    }

    // ================= INFO BOX =================

    private JPanel createInfoBox(String title, JLabel valueLabel, Color color) {

        JPanel box = new JPanel(new BorderLayout());

        box.setBackground(WHITE);

        box.setBorder(new EmptyBorder(15, 18, 15, 18));

        JLabel titleLabel = new JLabel(title);

        titleLabel.setForeground(MUTED);

        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 10));

        valueLabel.setForeground(color);

        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        box.add(titleLabel, BorderLayout.NORTH);

        box.add(valueLabel, BorderLayout.CENTER);

        return box;
    }

    // ================= PREVIEW CARD =================

    private JPanel createPreviewCard() {

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));

        card.setBackground(WHITE);

        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());

        header.setOpaque(false);

        JPanel left = new JPanel();

        left.setOpaque(false);

        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Dataset Preview");

        title.setForeground(TEXT);

        title.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel subtitle = new JLabel("First 5 records from your dataset");

        subtitle.setForeground(MUTED);

        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));

        left.add(title);

        left.add(Box.createVerticalStrut(4));

        left.add(subtitle);

        JPanel stats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        stats.setOpaque(false);

        rowsLabel = new JLabel("Rows: 0");

        columnsLabel = new JLabel("Columns: 0");

        rowsLabel.setForeground(PRIMARY);

        columnsLabel.setForeground(PRIMARY);

        rowsLabel.setFont(new Font("SansSerif", Font.BOLD, 11));

        columnsLabel.setFont(new Font("SansSerif", Font.BOLD, 11));

        stats.add(rowsLabel);

        stats.add(columnsLabel);

        header.add(left, BorderLayout.WEST);

        header.add(stats, BorderLayout.EAST);

        // ================= TABLE =================

        tableModel = new DefaultTableModel();

        previewTable = new JTable(tableModel);

        previewTable.setRowHeight(30);

        previewTable.setFont(new Font("SansSerif", Font.PLAIN, 12));

        previewTable.setForeground(TEXT);

        previewTable.setGridColor(new Color(235, 237, 243));

        previewTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        previewTable.getTableHeader().setBackground(new Color(245, 246, 250));

        previewTable.getTableHeader().setForeground(TEXT);

        JScrollPane tableScroll = new JScrollPane(previewTable);

        tableScroll.setBorder(new EmptyBorder(15, 0, 0, 0));

        card.add(header, BorderLayout.NORTH);

        card.add(tableScroll, BorderLayout.CENTER);

        return card;
    }

    // ================= FILE CHOOSER =================

    private void chooseFile() {

        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle("Select Student Dataset");

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            String name = file.getName().toLowerCase();

            if (!name.endsWith(".csv") && !name.endsWith(".xlsx") && !name.endsWith(".xls")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select a CSV or Excel file.",
                        "Invalid File",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            // remember this dataset for the rest of the app (Data Cleaning page checks this)
            DatasetManager.setDatasetPath(file.getAbsolutePath());

            updateFileInfo(file);

            runPythonEngine(file);
        }
    }

    // ================= UPDATE FILE INFO =================

    private void updateFileInfo(File file) {

        fileNameLabel.setText(file.getName());

        filePathLabel.setText(file.getAbsolutePath());

        double size = file.length() / 1024.0;

        fileSizeLabel.setText(String.format("%.2f KB", size));

        statusLabel.setText("Processing...");

        statusLabel.setForeground(ORANGE());
    }

    // =====================================================
    // PYTHON ENGINE  (fixed: now shows the REAL error and
    // tries multiple interpreter names instead of failing
    // silently with a generic message)
    // =====================================================

    private void runPythonEngine(File file) {

        SwingWorker<String, Void> worker = new SwingWorker<>() {

            @Override
            protected String doInBackground() throws Exception {

                String engine = findDataEngine();

                if (engine == null) {
                    throw new RuntimeException(
                            "data_engine.py not found. Checked these locations:\n"
                                    + String.join("\n", candidateEnginePaths())
                                    + "\n\nCopy data_engine.py into one of these folders,"
                                    + " or update findDataEngine() with the correct path."
                    );
                }

                RuntimeException lastError = null;

                // Try common interpreter names so this works regardless
                // of how Python is set up on this machine.
                for (String python : new String[] {"python", "python3", "py"}) {

                    try {
                        return runProcess(python, engine, file.getAbsolutePath());
                    } catch (IOException notFound) {
                        // this interpreter name doesn't exist on this machine, try the next
                    } catch (RuntimeException failed) {
                        // interpreter exists but the script itself failed —
                        // keep this real error and stop trying other names
                        lastError = failed;
                        break;
                    }
                }

                if (lastError != null) {
                    throw lastError;
                }

                throw new RuntimeException(
                        "Could not find a Python interpreter (tried python, python3, py). "
                                + "Is Python installed and on your PATH?"
                );
            }

            private String runProcess(String python, String engine, String datasetPath) throws Exception {

                ProcessBuilder builder = new ProcessBuilder(python, engine, datasetPath);

                builder.redirectErrorStream(true);

                Process process = builder.start();

                StringBuilder output = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                }

                int exitCode = process.waitFor();

                if (exitCode != 0) {
                    // this is the part the original code was dropping —
                    // the real traceback text from Python
                    throw new RuntimeException(
                            "Python exited with code " + exitCode + ":\n" + output
                    );
                }

                return output.toString();
            }

            @Override
            protected void done() {

                try {

                    String json = get();

                    processEngineResult(json);

                } catch (Exception e) {

                    statusLabel.setText("Processing Failed");

                    statusLabel.setForeground(Color.RED);

                    Throwable cause = e.getCause() != null ? e.getCause() : e;

                    JTextArea area = new JTextArea(String.valueOf(cause.getMessage()));
                    area.setEditable(false);
                    area.setLineWrap(true);
                    area.setWrapStyleWord(true);

                    JScrollPane scroll = new JScrollPane(area);
                    scroll.setPreferredSize(new Dimension(500, 220));

                    JOptionPane.showMessageDialog(
                            UploadDataset.this,
                            scroll,
                            "Data Engine Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };

        worker.execute();
    }

    // ================= LOCATE data_engine.py =================
    //
    // Checks the folders data_engine.py is most likely to be in,
    // so the app works whether you run it from the project root,
    // from inside src/, or right next to the compiled .class files.

    private java.util.List<String> candidateEnginePaths() {

        String userDir = System.getProperty("user.dir");

        java.util.List<String> candidates = new java.util.ArrayList<>();

        candidates.add(new File(userDir, "data_engine.py").getAbsolutePath());
        candidates.add(new File(userDir, "src/data_engine.py").getAbsolutePath());
        candidates.add(new File(userDir, "src/eduinsight/data_engine.py").getAbsolutePath());
        candidates.add(new File(userDir, "eduinsight/data_engine.py").getAbsolutePath());
        candidates.add(new File(userDir, "python_engine/data_engine.py").getAbsolutePath());
        candidates.add(new File(userDir, "../python_engine/data_engine.py").getAbsolutePath());

        return candidates;
    }

    private String findDataEngine() {

        for (String path : candidateEnginePaths()) {
            if (new File(path).exists()) {
                return path;
            }
        }

        return null;
    }

    // ================= PROCESS RESULT =================

    private void processEngineResult(String json) {

        /*
         * For now this method receives
         * JSON from Python.
         *
         * Gson can be used here to parse
         * the JSON properly.
         */

        if (json == null || json.trim().isEmpty()) {

            statusLabel.setText("No response from Data Engine");

            statusLabel.setForeground(Color.RED);

            return;
        }

        if (json.contains("\"success\": false") || json.contains("\"success\":false")) {

            statusLabel.setText("Processing Failed");

            statusLabel.setForeground(Color.RED);

            JOptionPane.showMessageDialog(
                    this,
                    json,
                    "Data Engine Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        /*
         * Temporary simple parser.
         *
         * Next step can replace this
         * with Gson for complete JSON parsing.
         */

        updatePreviewFromJson(json);
    }

    // ================= JSON PREVIEW =================

    private void updatePreviewFromJson(String json) {

        try {

            // Rows

            String rows = extractJsonValue(json, "\"rows\":");

            // Columns

            String columns = extractJsonValue(json, "\"columns\":");

            rowsLabel.setText("Rows: " + rows);

            columnsLabel.setText("Columns: " + columns);

            statusLabel.setText("Uploaded Successfully");

            statusLabel.setForeground(GREEN);

            /*
             * Preview data will be parsed
             * completely in the next version
             * using Gson.
             */

            JOptionPane.showMessageDialog(
                    this,
                    "Dataset successfully processed by Python Data Engine.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {

            statusLabel.setText("Processing Error");

            statusLabel.setForeground(Color.RED);
        }
    }

    // ================= SIMPLE JSON VALUE =================

    private String extractJsonValue(String json, String key) {

        int start = json.indexOf(key);

        if (start == -1) {
            return "0";
        }

        start += key.length();

        int end = json.indexOf(",", start);

        if (end == -1) {
            end = json.indexOf("}", start);
        }

        return json.substring(start, end).trim();
    }

    // ================= BUTTON STYLE =================

    private void stylePrimaryButton(JButton button) {

        button.setBackground(PRIMARY);

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setFont(new Font("SansSerif", Font.BOLD, 12));

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(new EmptyBorder(11, 20, 11, 20));
    }

    // ================= COLORS =================

    private Color BLUE() {
        return new Color(59, 130, 246);
    }

    private Color ORANGE() {
        return new Color(245, 158, 11);
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            UploadDataset page = new UploadDataset();

            page.setVisible(true);
        });
    }
}