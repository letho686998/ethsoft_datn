package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.KichCoDTO;
import com.ethsoft.mydatn.service.KichCoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kich-co")
@RequiredArgsConstructor
public class KichCoRestController {

    private final KichCoService service;

    @GetMapping("/getAll")
    public List<KichCoDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public KichCoDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public KichCoDTO add(@RequestBody KichCoDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/update/{id}")
    public KichCoDTO update(@PathVariable Long id, @RequestBody KichCoDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
