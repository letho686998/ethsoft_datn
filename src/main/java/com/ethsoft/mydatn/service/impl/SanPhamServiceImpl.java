// src/main/java/com/ethsoft/mydatn/service/impl/SanPhamServiceImpl.java
package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.*;
import com.ethsoft.mydatn.entity.*;
import com.ethsoft.mydatn.exception.ApiException;
import com.ethsoft.mydatn.repository.*;
import com.ethsoft.mydatn.service.SanPhamService;
import com.ethsoft.mydatn.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {

    private final SanPhamRepository sanPhamRepository;
    private final ThuongHieuRepository thuongHieuRepository;
    private final DanhMucRepository danhMucRepository;
    private final ChatLieuRepository chatLieuRepository;
    private final DeGiayRepository deGiayRepository;

//    private String genNextCode(String maxCode) {
//        int next = 1;
//        if (maxCode != null && maxCode.matches("SP\\d+")) {
//            next = Integer.parseInt(maxCode.substring(2)) + 1;
//        }
//        return "SP" + new DecimalFormat("000000").format(next);
//    }

    private SanPhamDTO toDTO(SanPhamEntity e) {
        if (e == null) return null;
        return SanPhamDTO.builder()
                .id(e.getId())
                .maSanPham(e.getMaSanPham())
                .tenSanPham(e.getTenSanPham())
                .moTa(e.getMoTa())
                .trangThai(e.getTrangThai())
                .gioiTinh(e.getGioiTinh())
                .ngayTao(e.getNgayTao())
                .thuongHieuId(e.getThuongHieu() != null ? e.getThuongHieu().getId() : null)
                .danhMucId(e.getDanhMuc() != null ? e.getDanhMuc().getId() : null)
                .chatLieuId(e.getChatLieu() != null ? e.getChatLieu().getId() : null)
                .deGiayId(e.getDeGiay() != null ? e.getDeGiay().getId() : null)
                .build();
    }

    private ThuongHieuEntity getThuongHieu(Long id) {
        return id == null ? null :
                thuongHieuRepository.findById(id)
                        .orElseThrow(() -> new ApiException("Không tìm thấy thương hiệu ID=" + id));
    }

    private DanhMucEntity getDanhMuc(Long id) {
        return id == null ? null :
                danhMucRepository.findById(id)
                        .orElseThrow(() -> new ApiException("Không tìm thấy danh mục ID=" + id));
    }

    private ChatLieuEntity getChatLieu(Long id) {
        return id == null ? null :
                chatLieuRepository.findById(id)
                        .orElseThrow(() -> new ApiException("Không tìm thấy chất liệu ID=" + id));
    }

    private DeGiayEntity getDeGiay(Long id) {
        return id == null ? null :
                deGiayRepository.findById(id)
                        .orElseThrow(() -> new ApiException("Không tìm thấy đế giày ID=" + id));
    }

    @Transactional
    @Override
    public SanPhamDTO create(SanPhamCreateRequest req) {
        String max = sanPhamRepository.findMaxMaSanPham();
//        String code = genNextCode(max);
        String code = CodeGenerator.nextSanPhamCode(max);

        SanPhamEntity e = SanPhamEntity.builder()
                .maSanPham(code)
                .tenSanPham(req.getTenSanPham())
                .moTa(req.getMoTa())
                .trangThai(req.getTrangThai() == null ? 1 : req.getTrangThai())
                .gioiTinh(req.getGioiTinh() == null ? 2 : req.getGioiTinh())
                .thuongHieu(getThuongHieu(req.getThuongHieuId()))
                .danhMuc(getDanhMuc(req.getDanhMucId()))
                .chatLieu(getChatLieu(req.getChatLieuId()))
                .deGiay(getDeGiay(req.getDeGiayId()))
                .build();

        sanPhamRepository.save(e);
        return toDTO(e);
    }

    @Transactional
    @Override
    public SanPhamDTO update(Long id, SanPhamUpdateRequest req) {
        SanPhamEntity e = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ApiException("Sản phẩm không tồn tại: " + id));

        if (req.getTenSanPham() != null) e.setTenSanPham(req.getTenSanPham());
        if (req.getMoTa() != null) e.setMoTa(req.getMoTa());
        if (req.getTrangThai() != null) e.setTrangThai(req.getTrangThai());
        if (req.getGioiTinh() != null) e.setGioiTinh(req.getGioiTinh());

        if (req.getThuongHieuId() != null) e.setThuongHieu(getThuongHieu(req.getThuongHieuId()));
        if (req.getDanhMucId() != null) e.setDanhMuc(getDanhMuc(req.getDanhMucId()));
        if (req.getChatLieuId() != null) e.setChatLieu(getChatLieu(req.getChatLieuId()));
        if (req.getDeGiayId() != null) e.setDeGiay(getDeGiay(req.getDeGiayId()));

        return toDTO(e);
    }

    @Override
    public SanPhamDTO getById(Long id) {
        SanPhamEntity e = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy sản phẩm: " + id));

        SanPhamDTO dto = toDTO(e);

        // 🔹 Bổ sung tên hiển thị (nếu có)
        if (e.getThuongHieu() != null) {
            dto.setTenThuongHieu(e.getThuongHieu().getTenThuongHieu());
        }
        if (e.getDanhMuc() != null) {
            dto.setTenDanhMuc(e.getDanhMuc().getTenDanhMuc());
        }
        if (e.getChatLieu() != null) {
            dto.setTenChatLieu(e.getChatLieu().getTenChatLieu());
        }
        if (e.getDeGiay() != null) {
            dto.setTenDeGiay(e.getDeGiay().getTenDeGiay());
        }

        return dto;
    }

    @Override
    public List<SanPhamDTO> getAllForList() {
        List<Object[]> rows = sanPhamRepository.findAllForList();

        return rows.stream().map(r -> {
            SanPhamDTO dto = new SanPhamDTO();
            dto.setId(((Number) r[0]).longValue());
            dto.setMaSanPham((String) r[1]);
            dto.setTenSanPham((String) r[2]);
            dto.setTrangThai((Integer) r[3]);

            // 👇 Chuyển ngày đúng kiểu
            dto.setNgayTao(
                    r[4] instanceof java.time.LocalDateTime
                            ? (java.time.LocalDateTime) r[4]
                            : null
            );

            dto.setThuongHieuId(r[5] != null ? ((Number) r[5]).longValue() : null);
            dto.setDanhMucId(r[6] != null ? ((Number) r[6]).longValue() : null);
            dto.setTongSoLuong(r[7] != null ? ((Number) r[7]).intValue() : 0);
            return dto;
        }).collect(Collectors.toList());
    }


}

