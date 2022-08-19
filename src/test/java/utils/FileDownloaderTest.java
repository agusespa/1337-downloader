package utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileDownloaderTest {

    FileDownloader fileDownloader = new FileDownloader("https://tretton37.com/", "assets/i/join.jpg", "assets/i");

    @AfterAll
    public void cleanUp() throws IOException {
        Files.delete(Path.of("assets/i/join.jpg"));
        Files.delete(Path.of("assets/i/"));
        Files.delete(Path.of("assets/"));
    }

    @Test
    @Order(1)
    public void shouldMakeDir() {
        fileDownloader.makeDirs();
        assertTrue(Files.isDirectory(Paths.get("assets/i")));
    }

    @Test
    @Order(2)
    public void shouldDownloadFile() {
        fileDownloader.makeDirs();
        fileDownloader.downloadFile();
        assertTrue(Files.exists(Paths.get("assets/i/join.jpg")));
    }
}
