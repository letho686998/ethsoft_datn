package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.MauSacDTO;
import com.ethsoft.mydatn.service.MauSacService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mau-sac")
@RequiredArgsConstructor
public class MauSacRestController {

    private final MauSacService service;

    @GetMapping("/getAll")
    public List<MauSacDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MauSacDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public MauSacDTO add(@RequestBody MauSacDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/update/{id}")
    public MauSacDTO update(@PathVariable Long id, @RequestBody MauSacDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
