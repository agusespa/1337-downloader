package utils;

import java.util.ArrayList;
import java.util.List;
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
}
