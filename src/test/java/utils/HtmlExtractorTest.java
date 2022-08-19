package utils;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtmlExtractorTest {

    String htmlSample = HtmlStringTestSample.sample;
    String regex = "\"[0-9a-zA-Z/_%\\-.]*\"";

    @Test
    public void shouldExtractHtmlTitle() {
        String expected = "Your trusted technology partner | tretton37";
        assertEquals(expected, HtmlExtractor.extractTitle(htmlSample));
    }

    @Test
    public void shouldExtractAnchors() {
        Set<String> expected = new HashSet<>();
        expected.add("who-we-are");

        Set<String> returned = HtmlExtractor.getAnchorList(htmlSample);

        assertEquals(expected, returned);
    }
}
