import utils.FileDownloader;
import utils.HtmlExtractor;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class App {

    public static void main(String[] args) {

        System.out.println("Downloading files started...");

        String baseUrl = "https://tretton37.com/";

        Set<String> visitedAnchors = new HashSet<>();

        Set<String> downloadedFiles = new HashSet<>();

        String anchor = ""; // starting at the home page
        recursiveTraverse(baseUrl, anchor, visitedAnchors, downloadedFiles);

        System.out.println("********");
        System.out.println("Download of tretton37.com finished successfully!");
    }

    static void recursiveTraverse(String baseUrl, String anchor, Set<String> visitedAnchors, Set<String> downloadedFiles) {

        visitedAnchors.add(anchor);

        Set<String> anchors = new HashSet<>();


        try {
            String parsedHtml = readAndWriteHtml(baseUrl, anchor);

            anchors = HtmlExtractor.getAnchorList(parsedHtml);

            // extracts the file's paths in the html according to regexes
            Set<String> paths = new HashSet<>();
            String regex = "\"[0-9a-zA-Z/_%\\-.]*\"";
            Pattern hrefPattern = Pattern.compile("href=" + regex);
            paths.addAll(HtmlExtractor.getFilePathList(hrefPattern, parsedHtml));
            Pattern srcPattern = Pattern.compile("src=" + regex);
            paths.addAll(HtmlExtractor.getFilePathList(srcPattern, parsedHtml));

            downloadAllFiles(baseUrl, anchor, paths, downloadedFiles);

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String a : anchors) {
            if (!visitedAnchors.contains(a)) {
                recursiveTraverse(baseUrl, a, visitedAnchors, downloadedFiles);
            }
        }
    }

    private static void downloadAllFiles(String baseUrl, String anchor, Set<String> paths, Set<String> downloadedFiles) {
        ExecutorService threadPool = Executors.newFixedThreadPool(paths.size());
        System.out.print("Downloading files from " + (anchor.equals("") ? "index" : anchor));

        // downloads each file asynchronously as well as creates its directories
        for (String path : paths) {
            if (downloadedFiles.contains(path)) continue;
            int startIndex = path.charAt(0) == '/' ? 1 : 0; // handles parsing of both absolute and relative paths
            String relativeFilePath = path.substring(startIndex);

            FileDownloader fileDownloader = new FileDownloader(baseUrl, relativeFilePath);
            threadPool.execute(fileDownloader);

            downloadedFiles.add(path);
        }

        // waits until all files are downloaded to print confirmation
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(30, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println(" download complete!");
    }

    private static String readAndWriteHtml(String baseUrl, String anchor) throws IOException {
        URL url = new URL(baseUrl + anchor);
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        StringBuilder htmlString = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            htmlString.append(line);
        }
        reader.close();

        String title = HtmlExtractor.extractTitle(htmlString.toString());

        BufferedWriter writer = new BufferedWriter(new FileWriter(title + ".html"));
        writer.write(htmlString.toString());
        writer.close();

        return htmlString.toString();
    }
}
