package com.rustam.e_commerce.service;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FileStorageService {

    private final String SAVE_URL = "D:\\projects\\e-commerce-photos\\uploads\\";

    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(SAVE_URL + fileName);
            Files.createDirectories(filePath.getParent()); // Qovluq yoxdursa, yaradılır
            Files.write(filePath, file.getBytes());
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Fayl saxlanılmadı: " + e.getMessage());
        }
    }
}
