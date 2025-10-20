package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.MauSacDTO;
import com.ethsoft.mydatn.entity.MauSacEntity;
import com.ethsoft.mydatn.exception.ApiException;
import com.ethsoft.mydatn.repository.MauSacRepository;
import com.ethsoft.mydatn.service.MauSacService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MauSacServiceImpl implements MauSacService {

    private final MauSacRepository repo;

    // ✅ Chuyển Entity -> DTO
    private MauSacDTO toDTO(MauSacEntity e) {
        return MauSacDTO.builder()
                .id(e.getId())
                .tenMau(e.getTenMau())
                .maMau(e.getMaMau())
                .trangThai(e.getTrangThai())
                .ngayTao(e.getNgayTao())
                .build();
    }

    // ✅ Lấy tất cả màu sắc
    @Override
    public List<MauSacDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ Lấy chi tiết theo ID
    @Override
    public MauSacDTO getById(Long id) {
        return repo.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ApiException("Không tìm thấy màu sắc có ID: " + id));
    }

    // ✅ Tạo mới
    @Transactional
    @Override
    public MauSacDTO create(MauSacDTO dto) {
        if (repo.existsByTenMau(dto.getTenMau()))
            throw new ApiException("Tên màu đã tồn tại!");
        if (repo.existsByMaMau(dto.getMaMau()))
            throw new ApiException("Mã màu đã tồn tại!");

        MauSacEntity e = new MauSacEntity();
        e.setTenMau(dto.getTenMau());
        e.setMaMau(dto.getMaMau());
        e.setTrangThai(dto.getTrangThai() == null ? 1 : dto.getTrangThai());

        repo.save(e);
        return toDTO(e);
    }

    // ✅ Cập nhật
    @Transactional
    @Override
    public MauSacDTO update(Long id, MauSacDTO dto) {
        MauSacEntity e = repo.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy màu sắc: " + id));

        if (dto.getTenMau() != null && !dto.getTenMau().equals(e.getTenMau())) {
            if (repo.existsByTenMau(dto.getTenMau()))
                throw new ApiException("Tên màu đã tồn tại!");
            e.setTenMau(dto.getTenMau());
        }

        if (dto.getMaMau() != null && !dto.getMaMau().equals(e.getMaMau())) {
            if (repo.existsByMaMau(dto.getMaMau()))
                throw new ApiException("Mã màu đã tồn tại!");
            e.setMaMau(dto.getMaMau());
        }

        if (dto.getTrangThai() != null)
            e.setTrangThai(dto.getTrangThai());

        return toDTO(e);
    }

    // ✅ Xóa
    @Transactional
    @Override
    public void delete(Long id) {
        if (!repo.existsById(id))
            throw new ApiException("Không tìm thấy màu sắc để xóa: " + id);
        repo.deleteById(id);
    }
}
