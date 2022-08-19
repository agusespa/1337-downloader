package utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloader {

    private String baseUrl;

    private String filePath;

    private String dirPath;

    public FileDownloader(String baseUrl, String filePath, String dirPath) {
        this.baseUrl = baseUrl;
        this.filePath = filePath;
        this.dirPath = dirPath;
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

    public String getDirPath() {
        return dirPath;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
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
        Path newDirectoryPath = Paths.get(dirPath);
        try {
            Files.createDirectories(newDirectoryPath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
