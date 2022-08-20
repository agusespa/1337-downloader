package utils;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtmlExtractorTest {

    String htmlSample = HtmlStringTestSample.sample;

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

    @Test
    public void shouldExtractHrefs() {
        Set<String> expected = new HashSet<>();
        expected.add("/assets/i/join.jpg");

        Set<String> returned = HtmlExtractor.getFilePathList("href=", htmlSample);

        assertEquals(expected, returned);
    }

    @Test
    public void shouldExtractSrcs() {
        Set<String> expected = new HashSet<>();
        expected.add("/assets/i/_tretton37_slogan_white.svg");

        Set<String> returned = HtmlExtractor.getFilePathList("src=", htmlSample);

        assertEquals(expected, returned);
    }
}
