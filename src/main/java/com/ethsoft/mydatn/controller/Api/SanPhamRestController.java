// src/main/java/com/ethsoft/mydatn/controller/Api/SanPhamRestController.java
package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.*;
import com.ethsoft.mydatn.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/san-pham")
@RequiredArgsConstructor
public class SanPhamRestController {

    private final SanPhamService sanPhamService;

    // 👉 /api/san-pham/getAll  (trang danh sách gọi)
    @GetMapping("/getAll")
    public List<SanPhamDTO> getAll() {
        // Nếu bạn có service/feign để fill tên thương hiệu/danh mục, có thể enrich ở đây
        return sanPhamService.getAllForList();
    }

    // 👉 /api/san-pham/{id}  (xem/đổ form)
    @GetMapping("/{id}")
    public SanPhamDTO getOne(@PathVariable Long id) {
        return sanPhamService.getById(id);
    }

    // 👉 /api/san-pham/add  (popup lưu trên trang danh sách)
    @PostMapping("/add")
    public SanPhamDTO add(@RequestBody SanPhamCreateRequest req) {
        return sanPhamService.create(req);
    }

    // 👉 /api/san-pham/update/{id}  (đổi trạng thái & cập nhật)
    @PutMapping("/update/{id}")
    public SanPhamDTO update(@PathVariable Long id, @RequestBody SanPhamUpdateRequest req) {
        return sanPhamService.update(id, req);
    }

    // 👉 /api/san-pham/them-san-pham  (trang thêm sản phẩm mới gọi khi bấm Lưu)
    @PostMapping("/them-san-pham")
    public SanPhamDTO createFromAddPage(@RequestBody SanPhamCreateRequest req) {
        return sanPhamService.create(req);
    }
}
