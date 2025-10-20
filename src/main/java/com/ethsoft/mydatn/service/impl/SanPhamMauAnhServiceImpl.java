// src/main/java/com/ethsoft/mydatn/service/impl/SanPhamMauAnhServiceImpl.java
package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.SanPhamMauAnhCreateRequest;
import com.ethsoft.mydatn.dto.SanPhamMauAnhDTO;
import com.ethsoft.mydatn.entity.MauSacEntity;
import com.ethsoft.mydatn.entity.SanPhamEntity;
import com.ethsoft.mydatn.entity.SanPhamMauAnhEntity;
import com.ethsoft.mydatn.repository.MauSacRepository;
import com.ethsoft.mydatn.repository.SanPhamMauAnhRepository;
import com.ethsoft.mydatn.repository.SanPhamRepository;
import com.ethsoft.mydatn.service.SanPhamMauAnhService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamMauAnhServiceImpl implements SanPhamMauAnhService {

    private final SanPhamRepository sanPhamRepository;
    private final SanPhamMauAnhRepository spmaRepository;
    private final MauSacRepository mauSacRepository;

    private SanPhamMauAnhDTO toDTO(SanPhamMauAnhEntity e) {
        return SanPhamMauAnhDTO.builder()
                .id(e.getId())
                .sanPhamId(e.getSanPham().getId())
                .mauSacId(e.getMauSac() != null ? e.getMauSac().getId() : null)
                .duongDan(e.getDuongDan())
                .moTa(e.getMoTa())
                .laAnhBia(e.getLaAnhBia())
                .thuTu(e.getThuTu())
                .nguon(e.getNguon())
                .build();
    }

    private MauSacEntity getMauSac(Long mauSacId) {
        return mauSacRepository.findById(mauSacId)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy màu sắc ID=" + mauSacId));
    }

    @Transactional
    @Override
    public List<SanPhamMauAnhDTO> saveByColor(Long sanPhamId, Long mauSacId, List<SanPhamMauAnhCreateRequest> reqs) {
        SanPhamEntity sp = sanPhamRepository.findById(sanPhamId)
                .orElseThrow(() -> new NoSuchElementException("Sản phẩm không tồn tại: " + sanPhamId));

        MauSacEntity mauSac = getMauSac(mauSacId);
        List<SanPhamMauAnhEntity> old = spmaRepository
                .findBySanPham_IdAndMauSac_IdOrderByThuTuAsc(sanPhamId, mauSacId);
        spmaRepository.deleteAll(old);

        int idx = 0;
        List<SanPhamMauAnhEntity> batch = new ArrayList<>();
        for (SanPhamMauAnhCreateRequest r : reqs) {
            batch.add(SanPhamMauAnhEntity.builder()
                    .sanPham(sp)
                    .mauSac(mauSac)
                    .duongDan(r.getDuongDan())
                    .moTa(r.getMoTa())
                    .laAnhBia(Boolean.TRUE.equals(r.getLaAnhBia()))
                    .thuTu(r.getThuTu() == null ? idx : r.getThuTu())
                    .nguon(r.getNguon())
                    .build());
            idx++;
        }
        return spmaRepository.saveAll(batch).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SanPhamMauAnhDTO> listByColor(Long sanPhamId, Long mauSacId) {
        return spmaRepository.findBySanPham_IdAndMauSac_IdOrderByThuTuAsc(sanPhamId, mauSacId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<SanPhamMauAnhDTO> listByProduct(Long sanPhamId) {
        return spmaRepository.findBySanPham_IdOrderByThuTuAsc(sanPhamId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }
}
