package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${app.upload.root-dir:uploads/sanpham}")
    private String uploadRoot; // "uploads/sanpham"

    @Value("${app.upload.base-url:http://localhost:8080/uploads/sanpham}")
    private String baseUrl; // "http://localhost:8080/uploads/sanpham"

    @Override
    public String saveProductImage(Long sanPhamId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("❌ File upload trống!");
        }

        try {
            String rootDir = System.getProperty("user.dir") + "/uploads/sanpham";
            Path dirPath = Paths.get(rootDir, sanPhamId.toString());
            Files.createDirectories(dirPath);

            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String ext = Optional.ofNullable(file.getOriginalFilename())
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(f.lastIndexOf(".")))
                    .orElse(".jpg");

            String newName = UUID.randomUUID() + "_" + timestamp + ext;
            Path fullPath = dirPath.resolve(newName);

            Files.copy(file.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);
            String fileUrl = baseUrl + "/" + sanPhamId + "/" + newName;

            log.info("✅ Lưu file thành công: {}", fileUrl);
            return fileUrl;

        } catch (IOException e) {
            throw new RuntimeException("❌ Lỗi khi lưu file: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean deleteFile(String relativePath) throws IOException {
        Path filePath = Paths.get(relativePath);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
            return true;
        }
        return false;
    }
}
