package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.service.impl.FileStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileUploadRestController {

    private final FileStorageServiceImpl fileStorageService;

    /**
     * Upload ảnh sản phẩm theo ID sản phẩm
     * @param sanPhamId ID sản phẩm
     * @param file file ảnh multipart
     * @return JSON chứa đường dẫn public
     */
    @PostMapping("/sanpham/{sanPhamId}")
    public ResponseEntity<?> uploadSanPhamImage(
            @PathVariable Long sanPhamId,
            @RequestParam("file") MultipartFile file) {

        String url = fileStorageService.saveProductImage(sanPhamId, file);
        return ResponseEntity.ok(Map.of("duongDan", url));
    }
}
