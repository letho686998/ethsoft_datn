// src/main/java/com/ethsoft/mydatn/service/impl/FileStorageServiceImpl.java
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
import java.util.UUID;

@Slf4j
@Service
public class FileStorageServiceImpl implements FileStorageService {

    // /datn/uploads/sanpham
    @Value("${app.upload.root-abs}")
    private String uploadRootAbs;

    // http://localhost:8080/uploads/sanpham/
    @Value("${app.upload.public-base}")
    private String publicBase;

    @Override
    public String saveProductImage(Long sanPhamId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File rỗng!");
        }
        try {
            Path productDir = Paths.get(uploadRootAbs, String.valueOf(sanPhamId)).normalize();
            Files.createDirectories(productDir);

            String ext = getExt(file.getOriginalFilename());
            String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = UUID.randomUUID() + "_" + stamp + (ext.isEmpty() ? ".jpg" : ext);
            Path out = productDir.resolve(fileName);

            Files.copy(file.getInputStream(), out, StandardCopyOption.REPLACE_EXISTING);
            String url = (publicBase.endsWith("/") ? publicBase : publicBase + "/") + sanPhamId + "/" + fileName;
            log.info("Saved image: {}", out);
            return url;
        } catch (IOException e) {
            throw new RuntimeException("Không thể lưu file ảnh!", e);
        }
    }

    @Override
    public boolean deleteProductImage(Long sanPhamId, String fileName) {
        try {
            Path p = Paths.get(uploadRootAbs, String.valueOf(sanPhamId), fileName).normalize();
            return Files.deleteIfExists(p);
        } catch (IOException e) {
            log.error("Delete fail: {}/{} - {}", sanPhamId, fileName, e.getMessage());
            return false;
        }
    }

    private static String getExt(String name) {
        if (name == null) return "";
        int dot = name.lastIndexOf('.');
        return dot >= 0 ? name.substring(dot) : "";
    }
}
