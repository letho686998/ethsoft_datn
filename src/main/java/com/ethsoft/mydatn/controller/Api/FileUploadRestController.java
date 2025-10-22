package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.HinhAnhSanPhamDTO;
import com.ethsoft.mydatn.service.FileStorageService;
import com.ethsoft.mydatn.service.HinhAnhSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

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

        String url = fileStorageService.saveProductImage(sanPhamId, file);

// Lấy tên file thực tế từ MultipartFile (hoặc từ URL đã tách sẵn)
        String originalName = file.getOriginalFilename();
        String fileName = (originalName != null)
                ? originalName.substring(originalName.lastIndexOf('/') + 1)
                : url.substring(url.lastIndexOf('/') + 1);

// Ghi DB
        return hinhAnhService.createOne(sanPhamId, mauSacId, fileName, url);


    }
}
