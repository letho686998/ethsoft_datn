package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.DanhMucDTO;
import com.ethsoft.mydatn.service.DanhMucService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danh-muc")
@RequiredArgsConstructor
public class DanhMucRestController {

    private final DanhMucService service;

    @GetMapping("/getAll")
    public List<DanhMucDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DanhMucDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public DanhMucDTO add(@RequestBody DanhMucDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/update/{id}")
    public DanhMucDTO update(@PathVariable Long id, @RequestBody DanhMucDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
