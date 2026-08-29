package eduinsight;

public class DatasetManager {

    private static String datasetPath;

    public static void setDatasetPath(
            String path
    ) {
        datasetPath = path;
    }

    public static String getDatasetPath() {
        return datasetPath;
    }

    public static boolean hasDataset() {

        return datasetPath != null
                && !datasetPath.isEmpty();
    }
}