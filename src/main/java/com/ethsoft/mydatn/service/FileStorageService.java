// src/main/java/com/ethsoft/mydatn/service/FileStorageService.java
package com.ethsoft.mydatn.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    /** Lưu file ảnh vào /datn/uploads/sanpham/{sanPhamId}/… và trả về URL public */
    String saveProductImage(Long sanPhamId, MultipartFile file);

    /** Xóa file vật lý theo tên */
    boolean deleteProductImage(Long sanPhamId, String fileName);
}
