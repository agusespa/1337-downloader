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
}
