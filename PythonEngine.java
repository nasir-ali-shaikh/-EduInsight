package eduinsight;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Shared logic for locating and calling data_engine.py.
 * Both UploadDataset and DataCleaning use this so the
 * "where is the script / which python command" logic only
 * has to be right in one place.
 */
public class PythonEngine {

    private PythonEngine() {
    }

    public static List<String> candidateEnginePaths() {

        String userDir = System.getProperty("user.dir");

        List<String> candidates = new ArrayList<>();

        candidates.add(new File(userDir, "data_engine.py").getAbsolutePath());
        candidates.add(new File(userDir, "src/data_engine.py").getAbsolutePath());
        candidates.add(new File(userDir, "src/eduinsight/data_engine.py").getAbsolutePath());
        candidates.add(new File(userDir, "eduinsight/data_engine.py").getAbsolutePath());
        candidates.add(new File(userDir, "python_engine/data_engine.py").getAbsolutePath());
        candidates.add(new File(userDir, "../python_engine/data_engine.py").getAbsolutePath());

        return candidates;
    }

    public static String findDataEngine() {

        for (String path : candidateEnginePaths()) {
            if (new File(path).exists()) {
                return path;
            }
        }

        return null;
    }

    /**
     * Runs data_engine.py with the given arguments (dataset path,
     * optionally a cleaning action keyword) and returns the raw
     * JSON text it printed. Throws a RuntimeException carrying the
     * real Python error/traceback if anything goes wrong.
     */
    public static String run(String... scriptArgs) throws Exception {

        String engine = findDataEngine();

        if (engine == null) {
            throw new RuntimeException(
                    "data_engine.py not found. Checked these locations:\n"
                            + String.join("\n", candidateEnginePaths())
                            + "\n\nCopy data_engine.py into one of these folders,"
                            + " or add its real location to PythonEngine.candidateEnginePaths()."
            );
        }

        RuntimeException lastError = null;

        for (String python : new String[] {"python", "python3", "py"}) {

            try {
                return runProcess(python, engine, scriptArgs);
            } catch (IOException notFound) {
                // this interpreter name isn't available on this machine, try the next
            } catch (RuntimeException failed) {
                // interpreter exists but the script itself failed — keep this
                // real error and stop trying other interpreter names
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

    private static String runProcess(String python, String engine, String[] scriptArgs) throws Exception {

        List<String> command = new ArrayList<>();
        command.add(python);
        command.add(engine);

        for (String arg : scriptArgs) {
            command.add(arg);
        }

        ProcessBuilder builder = new ProcessBuilder(command);
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
            throw new RuntimeException("Python exited with code " + exitCode + ":\n" + output);
        }

        return output.toString();
    }

    // ================= SIMPLE JSON VALUE =================
    //
    // Minimal helper for pulling one numeric/string value out of
    // the JSON text without needing a full JSON library.

    public static String extractJsonValue(String json, String key) {

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
}