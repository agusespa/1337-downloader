package utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileDownloaderTest {

    FileDownloader fileDownloader = new FileDownloader("https://tretton37.com/", "assets/i/join.jpg");

    @AfterAll
    public void cleanUp() throws IOException {
        Files.delete(Path.of("assets/i/join.jpg"));
        Files.delete(Path.of("assets/i/"));
        Files.delete(Path.of("assets/"));
    }

    @Test
    @Order(1)
    public void shouldReturnValidDirectoryPath() {
        String returned = fileDownloader.getDirectoryString("assets/i/join.jpg");
        String expected = "assets/i";
        assertEquals(expected, returned);
    }

    @Test
    @Order(2)
    public void shouldReturnEmptyDirectoryPathForFileAtRoot() {
        String returned = fileDownloader.getDirectoryString("join.jpg");
        String expected = "";
        assertEquals(expected, returned);
    }

    @Test
    @Order(3)
    public void shouldMakeDir() {
        fileDownloader.makeDirs();
        assertTrue(Files.isDirectory(Paths.get("assets/i")));
    }

    @Test
    @Order(4)
    public void shouldDownloadFile() {
        fileDownloader.makeDirs();
        fileDownloader.downloadFile();
        assertTrue(Files.exists(Paths.get("assets/i/join.jpg")));
    }
}
