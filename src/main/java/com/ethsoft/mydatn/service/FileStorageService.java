package com.ethsoft.mydatn.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FileStorageService {
    String saveProductImage(Long sanPhamId, MultipartFile file);
    boolean deleteFile(String relativePath) throws IOException;
}
