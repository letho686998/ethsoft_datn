// src/main/java/com/ethsoft/mydatn/controller/SanPhamChiTietRestController.java
package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.SanPhamChiTietCreateRequest;
import com.ethsoft.mydatn.dto.SanPhamChiTietDTO;
import com.ethsoft.mydatn.dto.SanPhamChiTietUpdateRequest;
import com.ethsoft.mydatn.service.SanPhamChiTietService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/san-pham-chi-tiet")
@RequiredArgsConstructor
public class SanPhamChiTietRestController {

    private final SanPhamChiTietService service;

    @GetMapping("/getAll")
    public List<SanPhamChiTietDTO> getAll() {
        return service.getAll();
    }

    @PostMapping("/bulk-create")
    public List<SanPhamChiTietDTO> bulkCreate(@RequestBody List<SanPhamChiTietCreateRequest> reqs){
        return service.createMany(reqs);
    }

    @PutMapping("/bulk-update")
    public List<SanPhamChiTietDTO> bulkUpdate(@RequestBody List<SanPhamChiTietUpdateRequest> reqs){
        return service.updateMany(reqs);
    }

    @GetMapping("/by-san-pham")
    public List<SanPhamChiTietDTO> getBySanPham(@RequestParam Long sanPhamId){
        return service.getBySanPham(sanPhamId);
    }
}

