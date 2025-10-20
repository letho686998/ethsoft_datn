package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.SanPhamChiTietRequest;
import com.ethsoft.mydatn.dto.SanPhamChiTietDTO;
import com.ethsoft.mydatn.entity.*;
import com.ethsoft.mydatn.exception.ApiException;
import com.ethsoft.mydatn.repository.*;
import com.ethsoft.mydatn.service.SanPhamChiTietService;
import com.ethsoft.mydatn.util.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    private final SanPhamRepository sanPhamRepository;
    private final SanPhamChiTietRepository spctRepository;
    private final MauSacRepository mauSacRepository;
    private final KichCoRepository kichCoRepository;
    private final SanPhamMauAnhRepository sanPhamMauAnhRepository;

    @Override
    public List<SanPhamChiTietDTO> getAll() {
        List<SanPhamChiTietEntity> entities = spctRepository.findAll();
        if (entities.isEmpty()) return new ArrayList<>();

        return entities.stream().map(spct -> {
            SanPhamChiTietDTO dto = toDTO(spct);

            // 🔹 Gắn ảnh bìa (nếu có)
            Optional<SanPhamMauAnhEntity> coverOpt =
                    sanPhamMauAnhRepository.findFirstBySanPham_IdAndMauSac_IdAndLaAnhBiaTrue(
                            spct.getSanPham().getId(),
                            spct.getMauSac().getId()
                    );

            coverOpt.ifPresent(cover -> dto.setAnhBiaUrl(cover.getDuongDan()));

            return dto;
        }).collect(Collectors.toList());
    }

//    @Override
//    public List<SanPhamChiTietDTO> getAll() {
//        List<Object[]> rows = spctRepository.findAllWithAnhBia();
//        List<SanPhamChiTietDTO> list = new ArrayList<>();
//
//        for (Object[] r : rows) {
//            SanPhamChiTietDTO dto = new SanPhamChiTietDTO();
//            dto.setId(((Number) r[0]).longValue());
//            dto.setSanPhamId(((Number) r[1]).longValue());
//            dto.setMauSacId(((Number) r[2]).longValue());
//            dto.setKichCoId(((Number) r[3]).longValue());
//            dto.setMaSpct((String) r[4]);
//            dto.setGiaNhap((BigDecimal) r[5]);
//            dto.setGiaBan((BigDecimal) r[6]);
//            dto.setSoLuongTon((Integer) r[7]);
//            dto.setTrangThai((Integer) r[8]);
//            dto.setMaSanPham((String) r[9]);
//            dto.setTenSanPham((String) r[10]);
//            dto.setTenMau((String) r[11]);
//            dto.setTenKichCo((String) r[12]);
//            dto.setAnhBiaUrl((String) r[13]); // 🎯 lấy ảnh bìa
//            list.add(dto);
//        }
//        return list;
//    }



    // 🔹 Convert Entity -> DTO
    // ✅ Mapper từ Entity sang DTO
    private SanPhamChiTietDTO toDTO(SanPhamChiTietEntity e) {
        if (e == null) return null;

        SanPhamChiTietDTO dto = new SanPhamChiTietDTO();
        dto.setId(e.getId());
        dto.setSanPhamId(e.getSanPham() != null ? e.getSanPham().getId() : null);
        dto.setMauSacId(e.getMauSac() != null ? e.getMauSac().getId() : null);
        dto.setKichCoId(e.getKichCo() != null ? e.getKichCo().getId() : null);

        dto.setMaSpct(e.getMaSpct());
        dto.setGiaNhap(e.getGiaNhap());
        dto.setGiaBan(e.getGiaBan());
        dto.setSoLuongTon(e.getSoLuongTon());
        dto.setTrangThai(e.getTrangThai());
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

        // ⚪ anhBiaUrl sẽ được gán ở bước sau khi lấy từ repo ảnh
        dto.setAnhBiaUrl(null);
        return dto;
    }

    // 🔹 Sinh mã SPCT
//    private String buildMaSPCT(String maSP, Long mauId, Long sizeId) {
//        return maSP + "-M" + mauId + "-S" + sizeId;
//    }

    private MauSacEntity getMauSac(Long id) {
        return mauSacRepository.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy màu sắc ID=" + id));
    }

    private KichCoEntity getKichCo(Long id) {
        return kichCoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy kích cỡ ID=" + id));
    }

    // 🔹 Bulk tạo SPCT
    @Transactional
    @Override
    public List<SanPhamChiTietDTO> bulkCreate(List<SanPhamChiTietRequest> reqs) {
        if (reqs == null || reqs.isEmpty()) return List.of();

        Long sanPhamId = reqs.get(0).getSanPhamId();
        if (sanPhamId == null)
            throw new ApiException("Thiếu sanPhamId trong danh sách SPCT!");

        SanPhamEntity sp = sanPhamRepository.findById(sanPhamId)
                .orElseThrow(() -> new ApiException("Sản phẩm không tồn tại: " + sanPhamId));

        List<SanPhamChiTietEntity> entities = new ArrayList<>();
        for (SanPhamChiTietRequest r : reqs) {
            MauSacEntity mau = getMauSac(r.getMauSacId());
            KichCoEntity size = getKichCo(r.getKichCoId());

            SanPhamChiTietEntity e = SanPhamChiTietEntity.builder()
                    .sanPham(sp)
                    .mauSac(mau)
                    .kichCo(size)
                    .soLuongTon(r.getSoLuongTon())
                    .giaNhap(r.getGiaNhap())
                    .giaBan(r.getGiaBan())
                    .trangThai(r.getTrangThai() == null ? 1 : r.getTrangThai())
                    .build();

            e.setMaSpct(CodeGenerator.buildMaSPCT(sp.getMaSanPham(), mau.getId(), size.getId()));
//            e.setMaSpct(buildMaSPCT(sp.getMaSanPham(), mau.getId(), size.getId()));
            entities.add(e);
        }

        entities = spctRepository.saveAll(entities);
        return entities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SanPhamChiTietDTO> bulkUpdate(List<SanPhamChiTietRequest> reqs) {
        if (reqs == null || reqs.isEmpty()) return List.of();

        List<SanPhamChiTietEntity> entities = new ArrayList<>();
        for (SanPhamChiTietRequest dto : reqs) {
            SanPhamChiTietEntity e = spctRepository.findById(dto.getId())
                    .orElseThrow(() -> new ApiException("Không tìm thấy SPCT ID=" + dto.getId()));

            if (dto.getSoLuongTon() != null) e.setSoLuongTon(dto.getSoLuongTon());
            if (dto.getGiaBan() != null) e.setGiaBan(dto.getGiaBan());
            if (dto.getTrangThai() != null) e.setTrangThai(dto.getTrangThai());
            if (dto.getNguoiCapNhat() != null)
                e.setNguoiCapNhat(dto.getNguoiCapNhat());
            if (dto.getNgayCapNhat() != null)
                e.setNgayCapNhat(dto.getNgayCapNhat());


            entities.add(e);
        }

        return spctRepository.saveAll(entities)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    // 🔹 Lấy danh sách SPCT theo sản phẩm
//    @Override
//    public List<SanPhamChiTietDTO> getBySanPham(Long sanPhamId) {
//        return spctRepository.findBySanPham_Id(sanPhamId)
//                .stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }
    @Override
    public List<SanPhamChiTietDTO> getBySanPham(Long sanPhamId) {
        List<SanPhamChiTietEntity> entities = spctRepository.findBySanPham_Id(sanPhamId);
        if (entities.isEmpty()) return new ArrayList<>();

        return entities.stream().map(spct -> {
            SanPhamChiTietDTO dto = toDTO(spct);

            // 🔹 Lấy ảnh bìa cho từng màu của sản phẩm này
            sanPhamMauAnhRepository
                    .findFirstBySanPham_IdAndMauSac_IdAndLaAnhBiaTrue(
                            spct.getSanPham().getId(),
                            spct.getMauSac().getId()
                    )
                    .ifPresent(ma -> dto.setAnhBiaUrl(ma.getDuongDan()));

            return dto;
        }).collect(Collectors.toList());
    }
}
