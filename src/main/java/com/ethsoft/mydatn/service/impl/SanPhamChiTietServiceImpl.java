package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.SanPhamChiTietCreateRequest;
import com.ethsoft.mydatn.dto.SanPhamChiTietDTO;
import com.ethsoft.mydatn.dto.SanPhamChiTietUpdateRequest;
import com.ethsoft.mydatn.entity.*;
import com.ethsoft.mydatn.exception.ApiException;
import com.ethsoft.mydatn.repository.*;
import com.ethsoft.mydatn.service.SanPhamChiTietService;
import com.ethsoft.mydatn.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {


    private final SanPhamRepository sanPhamRepository;
    private final SanPhamChiTietRepository spctRepository;
    private final MauSacRepository mauSacRepository;
    private final KichCoRepository kichCoRepository;
    private final HinhAnhSanPhamRepository sanPhamMauAnhRepository;

    @Lazy
    private final SanPhamServiceImpl sanPhamServiceImpl; // Inject service cha


    private SanPhamChiTietDTO toDTO(SanPhamChiTietEntity e) {
        if (e == null) return null;
        SanPhamChiTietDTO dto = new SanPhamChiTietDTO();

        dto.setId(e.getId());
        dto.setSanPhamId(e.getSanPham() != null ? e.getSanPham().getId() : null);
        dto.setMaSpct(e.getMaSpct());
        dto.setMauSacId(e.getMauSac() != null ? e.getMauSac().getId() : null);
        dto.setKichCoId(e.getKichCo() != null ? e.getKichCo().getId() : null);
        dto.setSoLuongTon(e.getSoLuongTon());
        dto.setGiaBan(e.getGiaBan());
        dto.setTrangThai(e.getTrangThai());
        dto.setGioiTinh(e.getGioiTinh());
        dto.setNguoiTao(e.getNguoiTao());
        dto.setNguoiCapNhat(e.getNguoiCapNhat());
        dto.setNgayTao(e.getNgayTao());
        dto.setNgayCapNhat(e.getNgayCapNhat());

        if (e.getSanPham() != null) {
            dto.setMaSanPham(e.getSanPham().getMaSanPham());
            dto.setTenSanPham(e.getSanPham().getTenSanPham());
        }
        if (e.getMauSac() != null) dto.setTenMau(e.getMauSac().getTenMau());
        if (e.getKichCo() != null) dto.setTenKichCo(e.getKichCo().getTenKichCo());

        // ✅ Lấy ảnh bìa (ưu tiên theo màu, fallback về ảnh chung)
        Long sanPhamId = e.getSanPham() != null ? e.getSanPham().getId() : null;
        Long mauSacId = e.getMauSac() != null ? e.getMauSac().getId() : null;

        String anhBiaUrl = sanPhamMauAnhRepository
                .findCoverBySanPhamAndMau(sanPhamId, mauSacId)
                .map(HinhAnhSanPhamEntity::getDuongDan)
                .orElseGet(() -> sanPhamMauAnhRepository
                        .findCoverBySanPhamAndMau(sanPhamId, null)
                        .map(HinhAnhSanPhamEntity::getDuongDan)
                        .orElse(null));

        dto.setAnhBiaUrl(anhBiaUrl);
        return dto;
    }




    @Transactional
    @Override
    public List<SanPhamChiTietDTO> createMany(List<SanPhamChiTietCreateRequest> reqs) {
        if (reqs == null || reqs.isEmpty()) return List.of();

        Long sanPhamId = reqs.get(0).getSanPhamId();
        SanPhamEntity sp = sanPhamRepository.findById(sanPhamId)
                .orElseThrow(() -> new ApiException("Không tìm thấy sản phẩm ID=" + sanPhamId));

        List<SanPhamChiTietEntity> entities = new ArrayList<>();

        for (SanPhamChiTietCreateRequest r : reqs) {
            MauSacEntity mau = mauSacRepository.findById(r.getMauSacId())
                    .orElseThrow(() -> new ApiException("Không tìm thấy màu ID=" + r.getMauSacId()));
            KichCoEntity kc = kichCoRepository.findById(r.getKichCoId())
                    .orElseThrow(() -> new ApiException("Không tìm thấy size ID=" + r.getKichCoId()));

            // 🔍 Kiểm tra xem biến thể này đã tồn tại chưa
            Optional<SanPhamChiTietEntity> existingOpt =
                    spctRepository.findBySanPham_IdAndMauSac_IdAndKichCo_Id(sanPhamId, mau.getId(), kc.getId());

            SanPhamChiTietEntity e;
            if (existingOpt.isPresent()) {
                // 🔁 Nếu đã tồn tại → cập nhật cộng dồn số lượng
                e = existingOpt.get();
                int oldQty = Optional.ofNullable(e.getSoLuongTon()).orElse(0);
                int addQty = Optional.ofNullable(r.getSoLuongTon()).orElse(0);
                e.setSoLuongTon(oldQty + addQty);
                if (r.getGiaBan() != null) e.setGiaBan(r.getGiaBan());
                if (r.getTrangThai() != null) e.setTrangThai(r.getTrangThai());
                if (r.getGioiTinh() != null) e.setGioiTinh(r.getGioiTinh());
                e.setNguoiCapNhat(r.getNguoiTao());
            } else {
                // 🆕 Nếu chưa có → tạo mới hoàn toàn
                e = SanPhamChiTietEntity.builder()
                        .sanPham(sp)
                        .mauSac(mau)
                        .kichCo(kc)
                        .soLuongTon(r.getSoLuongTon())
                        .giaBan(r.getGiaBan())
                        .trangThai(Optional.ofNullable(r.getTrangThai()).orElse(1))
                        .gioiTinh(Optional.ofNullable(r.getGioiTinh()).orElse(2))
                        .maSpct(CodeGenerator.buildMaSPCT(sp.getMaSanPham(), mau.getTenMau(), kc.getTenKichCo()))
                        .build();

                // ✅ Set audit thủ công sau khi build
                e.setNguoiTao(r.getNguoiTao());
                e.setNguoiCapNhat(r.getNguoiTao());
            }

            entities.add(spctRepository.save(e));
        }

        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }



    @Transactional
    @Override
    public List<SanPhamChiTietDTO> updateMany(List<SanPhamChiTietUpdateRequest> reqs) {
        if (reqs == null || reqs.isEmpty()) return List.of();

        List<SanPhamChiTietEntity> updates = new ArrayList<>();

        for (SanPhamChiTietUpdateRequest r : reqs) {
            if (r.getId() == null) continue;

            SanPhamChiTietEntity e = spctRepository.findById(r.getId())
                    .orElseThrow(() -> new ApiException("Không tìm thấy SPCT ID=" + r.getId()));

            if (r.getSoLuongTon() != null) e.setSoLuongTon(r.getSoLuongTon());
            if (r.getGiaBan() != null) e.setGiaBan(r.getGiaBan());
            if (r.getTrangThai() != null) e.setTrangThai(r.getTrangThai());
            if (r.getGioiTinh() != null) e.setGioiTinh(r.getGioiTinh());

            updates.add(e);
        }

        List<SanPhamChiTietEntity> saved = spctRepository.saveAll(updates);

        if (!saved.isEmpty()) {
            SanPhamEntity sp = saved.get(0).getSanPham();
            if (sp != null) {
                try {
                    sanPhamServiceImpl.capNhatTrangThaiChaTheoCon(sp.getId());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return saved.stream().map(this::toDTO).collect(Collectors.toList());
    }


    @Override
    public List<SanPhamChiTietDTO> getBySanPham(Long sanPhamId) {
        return spctRepository.findBySanPham_Id(sanPhamId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SanPhamChiTietDTO> getAll() {
        return spctRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
