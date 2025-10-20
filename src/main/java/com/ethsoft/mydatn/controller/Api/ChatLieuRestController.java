package com.ethsoft.mydatn.controller.Api;

import com.ethsoft.mydatn.dto.ChatLieuDTO;
import com.ethsoft.mydatn.service.ChatLieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat-lieu")
@RequiredArgsConstructor
public class ChatLieuRestController {

    private final ChatLieuService service;

    @GetMapping("/getAll")
    public List<ChatLieuDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ChatLieuDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public ChatLieuDTO add(@RequestBody ChatLieuDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/update/{id}")
    public ChatLieuDTO update(@PathVariable Long id, @RequestBody ChatLieuDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
