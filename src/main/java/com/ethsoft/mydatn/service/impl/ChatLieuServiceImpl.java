package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.ChatLieuDTO;
import com.ethsoft.mydatn.entity.ChatLieuEntity;
import com.ethsoft.mydatn.exception.ApiException;
import com.ethsoft.mydatn.repository.ChatLieuRepository;
import com.ethsoft.mydatn.service.ChatLieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatLieuServiceImpl implements ChatLieuService {

    private final ChatLieuRepository repo;

    private ChatLieuDTO toDTO(ChatLieuEntity e) {
        return ChatLieuDTO.builder()
                .id(e.getId())
                .tenChatLieu(e.getTenChatLieu())
                .trangThai(e.getTrangThai())
                .ngayTao(e.getNgayTao())
                .build();
    }

    @Override
    public List<ChatLieuDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ChatLieuDTO getById(Long id) {
        return repo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu: " + id));
    }

    @Transactional
    @Override
    public ChatLieuDTO create(ChatLieuDTO dto) {
        if (repo.existsByTenChatLieu(dto.getTenChatLieu()))
            throw new ApiException("Tên thương hiệu đã tồn tại!");
        ChatLieuEntity e = new ChatLieuEntity();
        e.setTenChatLieu(dto.getTenChatLieu());
        e.setTrangThai(dto.getTrangThai() == null ? 1 : dto.getTrangThai());
        repo.save(e);
        return toDTO(e);
    }

    @Transactional
    @Override
    public ChatLieuDTO update(Long id, ChatLieuDTO dto) {
        ChatLieuEntity e = repo.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu: " + id));
        if (dto.getTenChatLieu() != null)
            e.setTenChatLieu(dto.getTenChatLieu());
        if (dto.getTrangThai() != null)
            e.setTrangThai(dto.getTrangThai());
        return toDTO(e);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!repo.existsById(id))
            throw new ApiException("Thương hiệu không tồn tại!");
        repo.deleteById(id);
    }
}
