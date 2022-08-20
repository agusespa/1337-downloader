package utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlExtractor {

    public static String extractTitle(String html) {
        List<String> matchList = new ArrayList<>();

        Pattern regex = Pattern.compile("<title>(.*?)</title>");
        Matcher matcher = regex.matcher(html);

        StringBuilder rawTitle = new StringBuilder();
        if (matcher.find())
            rawTitle.append(matcher.group());

        return rawTitle.substring(7, rawTitle.length() - 8); // removes html tags
    }

    public static Set<String> getAnchorList(String html) {
        Pattern regex = Pattern.compile("href=\"/(.*?)\"");
        Set<String> pathList = new HashSet<>();
        List<String> links = extractLinks(regex, html);

        for (String s : links) {
            int startIndex = 7; // removes the html tag
            String anchor = s.substring(startIndex, s.length() -1);
            if (!anchor.contains(".")) // filters out file paths
                pathList.add(anchor);
        }

        return pathList;
    }

    public static Set<String> getFilePathList(Pattern regex, String html) {
        Set<String> pathList = new HashSet<>();
        List<String> links = extractLinks(regex, html);

        for (String s : links) {
            if (s.length() < 10 || !s.contains(".")) continue; // filters out non-file paths

            // removes html tag
            int startIndex = 0;
            for (int i = startIndex; i < s.length(); i++) {
                if (s.charAt(i) == '"') {
                    startIndex = i + 1;
                    break;
                }
            }

            pathList.add(s.substring(startIndex, s.length() - 1));
        }
        return pathList;
    }

    private static List<String> extractLinks(Pattern regex, String html) {
        List<String> matchList = new ArrayList<>();

        Matcher matcher = regex.matcher(html);

        while (matcher.find()) {
            matchList.add(matcher.group());
        }

        return matchList;
    }
}
