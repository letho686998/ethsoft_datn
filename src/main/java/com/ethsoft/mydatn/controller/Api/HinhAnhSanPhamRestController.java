package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.*;
import com.ethsoft.mydatn.service.HinhAnhSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hinh-anh-san-pham")
@RequiredArgsConstructor
public class HinhAnhSanPhamRestController {

    private final HinhAnhSanPhamService service;

    @GetMapping("/by-san-pham")
    public List<HinhAnhSanPhamDTO> getBySanPham(@RequestParam Long sanPhamId){
        return service.getBySanPham(sanPhamId);
    }

    @GetMapping("/by-san-pham-mau")
    public List<HinhAnhSanPhamDTO> getBySanPhamAndMau(@RequestParam Long sanPhamId, @RequestParam Long mauSacId){
        return service.getBySanPhamAndMau(sanPhamId, mauSacId);
    }

    @PostMapping("/bulk-create")
    public List<HinhAnhSanPhamDTO> createMany(@RequestBody List<HinhAnhSanPhamCreateRequest> reqs){
        return service.createMany(reqs);
    }

    @PutMapping("/update")
    public HinhAnhSanPhamDTO update(@RequestBody HinhAnhSanPhamUpdateRequest req){
        return service.update(req);
    }

    @PutMapping("/bulk-update")
    public List<HinhAnhSanPhamDTO> updateMany(@RequestBody List<HinhAnhSanPhamUpdateRequest> reqs){
        return service.updateMany(reqs);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PutMapping("/set-cover/{id}")
    public HinhAnhSanPhamDTO setCover(@PathVariable Long id) {
        return service.setCover(id);
    }

}
