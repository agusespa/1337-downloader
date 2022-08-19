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

        return rawTitle.substring(7, rawTitle.length() - 8);
    }

    public static Set<String> getAnchorList(String html) {
        Pattern regex = Pattern.compile("href=\"/(.*?)\"");
        Set<String> pathList = new HashSet<>();
        List<String> links = extractLinks(regex, html);

        for (String s : links) {
            int startIndex = 7; // removes the html tag
            String anchor = s.substring(startIndex, s.length() -1);
            if (!anchor.contains("."))
                pathList.add(anchor);
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
