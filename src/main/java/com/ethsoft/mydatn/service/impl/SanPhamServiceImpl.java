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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SanPhamServiceImpl implements SanPhamService {

    private final SanPhamRepository sanPhamRepository;
    private final ThuongHieuRepository thuongHieuRepository;
    private final DanhMucRepository danhMucRepository;
    private final ChatLieuRepository chatLieuRepository;
    private final DeGiayRepository deGiayRepository;
    private final SanPhamChiTietRepository sanPhamChiTietRepository;

    private SanPhamDTO toDTO(SanPhamEntity e) {
        if (e == null) return null;
        return SanPhamDTO.builder()
                .id(e.getId())
                .maSanPham(e.getMaSanPham())
                .tenSanPham(e.getTenSanPham())
                .moTa(e.getMoTa())
                .trangThai(e.getTrangThai())
                .gioiTinh(e.getGioiTinh())
                .nguoiTao(e.getNguoiTao())
                .nguoiCapNhat(e.getNguoiCapNhat())
                .ngayTao(e.getNgayTao())
                .ngayCapNhat(e.getNgayCapNhat())
                .thuongHieuId(e.getThuongHieu() != null ? e.getThuongHieu().getId() : null)
                .danhMucId(e.getDanhMuc() != null ? e.getDanhMuc().getId() : null)
                .chatLieuId(e.getChatLieu() != null ? e.getChatLieu().getId() : null)
                .deGiayId(e.getDeGiay() != null ? e.getDeGiay().getId() : null)
                .tenThuongHieu(e.getThuongHieu() != null ? e.getThuongHieu().getTenThuongHieu() : null)
                .tenDanhMuc(e.getDanhMuc() != null ? e.getDanhMuc().getTenDanhMuc() : null)
                .tenChatLieu(e.getChatLieu() != null ? e.getChatLieu().getTenChatLieu() : null)
                .tenDeGiay(e.getDeGiay() != null ? e.getDeGiay().getTenDeGiay() : null)
                .tongSoLuong(
                        e.getChiTietList() == null ? 0 :
                                e.getChiTietList().stream().mapToInt(c -> c.getSoLuongTon() == null ? 0 : c.getSoLuongTon()).sum()
                )
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

    @Transactional
    public SanPhamDTO updateTrangThai(Long sanPhamId, int trangThaiMoi) {
        SanPhamEntity sp = sanPhamRepository.findById(sanPhamId)
                .orElseThrow(() -> new ApiException("Không tìm thấy sản phẩm ID=" + sanPhamId));

        // Không cần làm gì nếu trạng thái không đổi
        if (Objects.equals(sp.getTrangThai(), trangThaiMoi)) {
            return toDTO(sp);
        }

        // Nếu đổi sang NGỪNG BÁN → Tất cả SPCT phải ngừng bán
        if (trangThaiMoi == 0) {
            List<SanPhamChiTietEntity> chiTietList = sanPhamChiTietRepository.findBySanPham_Id(sanPhamId);
            chiTietList.forEach(ct -> ct.setTrangThai(0));
            sanPhamChiTietRepository.saveAll(chiTietList);
            log.info("→ Ngừng bán toàn bộ SPCT của sản phẩm {}", sanPhamId);
        }

        // Nếu đổi sang ĐANG BÁN → Toàn bộ SPCT sẽ mở bán
        else if (trangThaiMoi == 1) {
            List<SanPhamChiTietEntity> chiTietList = sanPhamChiTietRepository.findBySanPham_Id(sanPhamId);
            chiTietList.forEach(ct -> ct.setTrangThai(1));
            sanPhamChiTietRepository.saveAll(chiTietList);
            log.info("→ Mở bán toàn bộ SPCT của sản phẩm {}", sanPhamId);
        }

        sp.setTrangThai(trangThaiMoi);
        sanPhamRepository.save(sp);
        return toDTO(sp);
    }

    /**
     * ✅ Hàm tự động cập nhật trạng thái CHA theo trạng thái CON
     * (Nếu tất cả con ngừng bán → cha ngừng bán,
     *  nếu có ít nhất 1 con đang bán → cha đang bán)
     */
    @Transactional
    public void capNhatTrangThaiChaTheoCon(Long sanPhamId) {
        SanPhamEntity sp = sanPhamRepository.findById(sanPhamId)
                .orElseThrow(() -> new ApiException("Không tìm thấy sản phẩm cha ID=" + sanPhamId));

        long soConDangBan = sanPhamChiTietRepository.countBySanPham_IdAndTrangThai(sanPhamId, 1);
        int newStatus = soConDangBan > 0 ? 1 : 0;

        if (!Objects.equals(sp.getTrangThai(), newStatus)) {
            sp.setTrangThai(newStatus);
            sanPhamRepository.save(sp);
            log.info("→ Đồng bộ trạng thái cha (ID={}) = {}", sanPhamId, newStatus);
        }
    }

    @Override
    public SanPhamDTO getById(Long id) {
        return toDTO(sanPhamRepository.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy sản phẩm: " + id)));
    }

    @Override
    public List<SanPhamDTO> getAll() {
        return sanPhamRepository.findAll().stream()
                .sorted((a,b) -> b.getId().compareTo(a.getId()))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}


