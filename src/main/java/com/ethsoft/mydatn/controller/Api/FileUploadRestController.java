package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.HinhAnhSanPhamDTO;
import com.ethsoft.mydatn.service.FileStorageService;
import com.ethsoft.mydatn.service.HinhAnhSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileUploadRestController {

    private final FileStorageService fileStorageService;
    private final HinhAnhSanPhamService hinhAnhService;

    @PostMapping("/hinh-anh-san-pham")
    public HinhAnhSanPhamDTO uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("sanPhamId") Long sanPhamId,
            @RequestParam(value = "mauSacId", required = false) Long mauSacId) {

        // Lưu ảnh vật lý
        String url = fileStorageService.saveProductImage(sanPhamId, file);

        // Tạo bản ghi ảnh trong DB
        return hinhAnhService.createOne(sanPhamId, mauSacId, file.getOriginalFilename(), url);
    }
}
