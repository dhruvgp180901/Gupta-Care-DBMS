package com.example.DBMS.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    public static void saveFile(String filename, String uploadDir, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        // iske starting me "/" nahi hona chahiye bcc
        // System.out.println("FFF" + " " + uploadPath);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        // System.out.println("GGG");
        System.out.println("uploadPath: " + uploadPath);
        System.out.println("filename: " + filename);
        try {
            InputStream inputStream = file.getInputStream();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("filePath: " + filePath);
        }
        catch (Exception e) {
            throw new IOException("File could not be saved");
        }
    }
}