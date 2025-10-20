package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.ThuongHieuDTO;
import com.ethsoft.mydatn.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thuong-hieu")
@RequiredArgsConstructor
public class ThuongHieuRestController {

    private final ThuongHieuService service;

    @GetMapping("/getAll")
    public List<ThuongHieuDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ThuongHieuDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public ThuongHieuDTO add(@RequestBody ThuongHieuDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/update/{id}")
    public ThuongHieuDTO update(@PathVariable Long id, @RequestBody ThuongHieuDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
