package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.DanhMucDTO;
import com.ethsoft.mydatn.entity.DanhMucEntity;
import com.ethsoft.mydatn.exception.ApiException;
import com.ethsoft.mydatn.repository.DanhMucRepository;
import com.ethsoft.mydatn.service.DanhMucService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DanhMucServiceImpl implements DanhMucService {

    private final DanhMucRepository repo;

    private DanhMucDTO toDTO(DanhMucEntity e) {
        return DanhMucDTO.builder()
                .id(e.getId())
                .tenDanhMuc(e.getTenDanhMuc())
                .trangThai(e.getTrangThai())
                .ngayTao(e.getNgayTao())
                .build();
    }

    @Override
    public List<DanhMucDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public DanhMucDTO getById(Long id) {
        return repo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu: " + id));
    }

    @Transactional
    @Override
    public DanhMucDTO create(DanhMucDTO dto) {
        if (repo.existsByTenDanhMuc(dto.getTenDanhMuc()))
            throw new ApiException("Tên thương hiệu đã tồn tại!");
        DanhMucEntity e = new DanhMucEntity();
        e.setTenDanhMuc(dto.getTenDanhMuc());
        e.setTrangThai(dto.getTrangThai() == null ? 1 : dto.getTrangThai());
        repo.save(e);
        return toDTO(e);
    }

    @Transactional
    @Override
    public DanhMucDTO update(Long id, DanhMucDTO dto) {
        DanhMucEntity e = repo.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu: " + id));
        if (dto.getTenDanhMuc() != null)
            e.setTenDanhMuc(dto.getTenDanhMuc());
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
