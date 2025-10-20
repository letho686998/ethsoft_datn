package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.KichCoDTO;
import com.ethsoft.mydatn.entity.KichCoEntity;
import com.ethsoft.mydatn.exception.ApiException;
import com.ethsoft.mydatn.repository.KichCoRepository;
import com.ethsoft.mydatn.service.KichCoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KichCoServiceImpl implements KichCoService {

    private final KichCoRepository repo;

    private KichCoDTO toDTO(KichCoEntity e) {
        return KichCoDTO.builder()
                .id(e.getId())
                .tenKichCo(e.getTenKichCo())
                .trangThai(e.getTrangThai())
                .ngayTao(e.getNgayTao())
                .build();
    }

    @Override
    public List<KichCoDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public KichCoDTO getById(Long id) {
        return repo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu: " + id));
    }

    @Transactional
    @Override
    public KichCoDTO create(KichCoDTO dto) {
        if (repo.existsByTenKichCo(dto.getTenKichCo()))
            throw new ApiException("Tên thương hiệu đã tồn tại!");
        KichCoEntity e = new KichCoEntity();
        e.setTenKichCo(dto.getTenKichCo());
        e.setTrangThai(dto.getTrangThai() == null ? 1 : dto.getTrangThai());
        repo.save(e);
        return toDTO(e);
    }

    @Transactional
    @Override
    public KichCoDTO update(Long id, KichCoDTO dto) {
        KichCoEntity e = repo.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu: " + id));
        if (dto.getTenKichCo() != null)
            e.setTenKichCo(dto.getTenKichCo());
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
