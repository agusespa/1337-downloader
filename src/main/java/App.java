import utils.HtmlExtractor;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class App {

    public static void main(String[] args) {

        System.out.println("Downloading files...");

        String baseUrl = "https://tretton37.com/";

        Set<String> visitedAnchors = new HashSet<>();

        String anchor = ""; // home page
        recursiveTraverse(baseUrl, anchor, visitedAnchors);
    }


    static void recursiveTraverse(String baseUrl, String anchor, Set<String> visitedAnchors) {

        visitedAnchors.add(anchor);
        Set<String> anchors = new HashSet<>();

        try {
            URL url = new URL(baseUrl + anchor);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            StringBuilder parsedHtml = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                parsedHtml.append(line);
            }
            reader.close();

            String title = HtmlExtractor.extractTitle(parsedHtml.toString());

            BufferedWriter writer = new BufferedWriter(new FileWriter(title + ".html"));
            writer.write(parsedHtml.toString());
            writer.close();

            anchors = HtmlExtractor.getAnchorList(parsedHtml.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String a : anchors) {
            if (!visitedAnchors.contains(a)) {
                recursiveTraverse(baseUrl, a, visitedAnchors);
            }
        }
    }
}
