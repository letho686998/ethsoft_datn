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

    @GetMapping("/getAll")
    public List<SanPhamDTO> getAll() {
        return sanPhamService.getAll();
    }

    @GetMapping("/{id}")
    public SanPhamDTO getOne(@PathVariable Long id) {
        return sanPhamService.getById(id);
    }

    @PostMapping("/create")
    public SanPhamDTO create(@RequestBody SanPhamCreateRequest req) {
        return sanPhamService.create(req);
    }

    @PutMapping("/update/{id}")
    public SanPhamDTO update(@PathVariable Long id, @RequestBody SanPhamUpdateRequest req) {
        return sanPhamService.update(id, req);
    }

    @PutMapping("/update-trang-thai/{id}")
    public SanPhamDTO updateTrangThai(
            @PathVariable Long id,
            @RequestParam int trangThai) {
        return sanPhamService.updateTrangThai(id, trangThai);
    }

}

