package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.ThuongHieuDTO;
import com.ethsoft.mydatn.entity.ThuongHieuEntity;
import com.ethsoft.mydatn.exception.ApiException;
import com.ethsoft.mydatn.repository.ThuongHieuRepository;
import com.ethsoft.mydatn.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThuongHieuServiceImpl implements ThuongHieuService {

    private final ThuongHieuRepository repo;

    private ThuongHieuDTO toDTO(ThuongHieuEntity e) {
        return ThuongHieuDTO.builder()
                .id(e.getId())
                .tenThuongHieu(e.getTenThuongHieu())
                .trangThai(e.getTrangThai())
                .ngayTao(e.getNgayTao())
                .build();
    }

    @Override
    public List<ThuongHieuDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ThuongHieuDTO getById(Long id) {
        return repo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu: " + id));
    }

    @Transactional
    @Override
    public ThuongHieuDTO create(ThuongHieuDTO dto) {
        if (repo.existsByTenThuongHieu(dto.getTenThuongHieu()))
            throw new ApiException("Tên thương hiệu đã tồn tại!");
        ThuongHieuEntity e = new ThuongHieuEntity();
        e.setTenThuongHieu(dto.getTenThuongHieu());
        e.setTrangThai(dto.getTrangThai() == null ? 1 : dto.getTrangThai());
        repo.save(e);
        return toDTO(e);
    }

    @Transactional
    @Override
    public ThuongHieuDTO update(Long id, ThuongHieuDTO dto) {
        ThuongHieuEntity e = repo.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu: " + id));
        if (dto.getTenThuongHieu() != null)
            e.setTenThuongHieu(dto.getTenThuongHieu());
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
