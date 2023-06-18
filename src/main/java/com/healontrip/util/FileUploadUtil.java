package com.healontrip.util;

import java.io.*;
import java.nio.file.*;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("File could not saved: " + fileName, ioe);
        }
    }

    public static void deleteFile(String uploadDir, String fileName) throws IOException {
        Path uploadPath = Paths.get(uploadDir + "/" + fileName);

        boolean fileDeleted = Files.deleteIfExists(uploadPath);

        if (!fileDeleted) {
            throw new IOException("File does not exist: " + fileName);
        }
    }
}
