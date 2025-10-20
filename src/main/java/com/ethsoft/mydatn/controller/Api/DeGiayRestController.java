package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.DeGiayDTO;
import com.ethsoft.mydatn.service.DeGiayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/de-giay")
@RequiredArgsConstructor
public class DeGiayRestController {

    private final DeGiayService service;

    @GetMapping("/getAll")
    public List<DeGiayDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DeGiayDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public DeGiayDTO add(@RequestBody DeGiayDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/update/{id}")
    public DeGiayDTO update(@PathVariable Long id, @RequestBody DeGiayDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
