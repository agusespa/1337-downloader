package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloader implements Runnable {

    private String baseUrl;

    private String filePath;

    public FileDownloader(String baseUrl, String filePath) {
        this.baseUrl = baseUrl;
        this.filePath = filePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    void downloadFile() {
        try {
            URL targetUrl = new URL(baseUrl + filePath);
            ReadableByteChannel readableByteChannel = Channels.newChannel(targetUrl.openStream());
            FileOutputStream outputStream = new FileOutputStream(filePath);
            outputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            outputStream.close();
            readableByteChannel.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void makeDirs() {
        String directory = getDirectoryString(filePath);

        if (!directory.isEmpty()) { // handles edge case of files at root directory
            Path dirPath = Paths.get(directory);
            try {
                Files.createDirectories(dirPath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    String getDirectoryString(String filePath) {
        if (!filePath.contains("/")) return "";

        int endIndex = filePath.length()-1;

        // finds index to remove filename from path to create directory
        for (int i = endIndex; i > 0; i--) {
            if (filePath.charAt(i) == '/') {
                endIndex = i;
                break;
            }
        }

        return filePath.substring(0, endIndex);
    }

    @Override
    public void run() {
        makeDirs();
        downloadFile();
    }
}
