// src/main/java/com/ethsoft/mydatn/controller/SanPhamChiTietRestController.java
package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.SanPhamChiTietRequest;
import com.ethsoft.mydatn.dto.SanPhamChiTietDTO;
import com.ethsoft.mydatn.service.SanPhamChiTietService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/san-pham-chi-tiet")
@RequiredArgsConstructor
public class SanPhamChiTietRestController {

    private final SanPhamChiTietService service;

    // ✅ Lấy toàn bộ danh sách SPCT (dùng cho bảng 11 cột)
    @GetMapping("/getAll")
    public List<SanPhamChiTietDTO> getAll() {
        return service.getAll();
    }

    // 👉 /api/san-pham-chi-tiet/bulk (trang thêm sản phẩm gọi sau khi có sanPhamId)
    @PostMapping("/bulk")
    public List<SanPhamChiTietDTO> bulk(@RequestBody List<SanPhamChiTietRequest> reqs){
        return service.bulkCreate(reqs);
    }

    @PutMapping("/bulk-update")
    public List<SanPhamChiTietDTO> bulkUpdate(@RequestBody List<SanPhamChiTietRequest> reqs){
        return service.bulkUpdate(reqs);
    }

    // ✅ Lấy theo ID sản phẩm (hỗ trợ cả dạng query param và path)
    @GetMapping("/by-san-pham/{sanPhamId}")
    public List<SanPhamChiTietDTO> bySanPham(@PathVariable Long sanPhamId){
        return service.getBySanPham(sanPhamId);
    }

    @GetMapping("/by-san-pham")
    public List<SanPhamChiTietDTO> getBySanPhamQuery(@RequestParam Long sanPhamId) {
        return service.getBySanPham(sanPhamId);
    }
}
