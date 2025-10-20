package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.DeGiayDTO;
import com.ethsoft.mydatn.entity.DeGiayEntity;
import com.ethsoft.mydatn.exception.ApiException;
import com.ethsoft.mydatn.repository.DeGiayRepository;
import com.ethsoft.mydatn.service.DeGiayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeGiayServiceImpl implements DeGiayService {

    private final DeGiayRepository repo;

    private DeGiayDTO toDTO(DeGiayEntity e) {
        return DeGiayDTO.builder()
                .id(e.getId())
                .tenDeGiay(e.getTenDeGiay())
                .trangThai(e.getTrangThai())
                .ngayTao(e.getNgayTao())
                .build();
    }

    @Override
    public List<DeGiayDTO> getAll() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public DeGiayDTO getById(Long id) {
        return repo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu: " + id));
    }

    @Transactional
    @Override
    public DeGiayDTO create(DeGiayDTO dto) {
        if (repo.existsByTenDeGiay(dto.getTenDeGiay()))
            throw new ApiException("Tên thương hiệu đã tồn tại!");
        DeGiayEntity e = new DeGiayEntity();
        e.setTenDeGiay(dto.getTenDeGiay());
        e.setTrangThai(dto.getTrangThai() == null ? 1 : dto.getTrangThai());
        repo.save(e);
        return toDTO(e);
    }

    @Transactional
    @Override
    public DeGiayDTO update(Long id, DeGiayDTO dto) {
        DeGiayEntity e = repo.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu: " + id));
        if (dto.getTenDeGiay() != null)
            e.setTenDeGiay(dto.getTenDeGiay());
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
