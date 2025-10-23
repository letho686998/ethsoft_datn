package com.ethsoft.mydatn.service.impl;

import com.ethsoft.mydatn.dto.*;
import com.ethsoft.mydatn.entity.*;
import com.ethsoft.mydatn.exception.ApiException;
import com.ethsoft.mydatn.repository.*;
import com.ethsoft.mydatn.service.FileStorageService;
import com.ethsoft.mydatn.service.HinhAnhSanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HinhAnhSanPhamServiceImpl implements HinhAnhSanPhamService {

    private final HinhAnhSanPhamRepository repo;
    private final SanPhamRepository sanPhamRepository;
    private final MauSacRepository mauSacRepository;
    private final FileStorageService fileStorageService;

    private HinhAnhSanPhamDTO toDTO(HinhAnhSanPhamEntity e) {
        if (e == null) return null;
        return HinhAnhSanPhamDTO.builder()
                .id(e.getId())
                .sanPhamId(e.getSanPham() != null ? e.getSanPham().getId() : null)
                .mauSacId(e.getMauSac() != null ? e.getMauSac().getId() : null)
                .tenTapTin(e.getTenTapTin())
                .duongDan(e.getDuongDan())
                .laAnhBia(e.getLaAnhBia())
                .trangThai(e.getTrangThai())
                .maSanPham(e.getSanPham() != null ? e.getSanPham().getMaSanPham() : null)
                .tenSanPham(e.getSanPham() != null ? e.getSanPham().getTenSanPham() : null)
                .tenMau(e.getMauSac() != null ? e.getMauSac().getTenMau() : null)
                .nguoiTao(e.getNguoiTao())
                .nguoiCapNhat(e.getNguoiCapNhat())
                .ngayTao(e.getNgayTao())
                .ngayCapNhat(e.getNgayCapNhat())
                .build();
    }

    @Transactional
    @Override
    public List<HinhAnhSanPhamDTO> createMany(List<HinhAnhSanPhamCreateRequest> reqs) {
        if (reqs == null || reqs.isEmpty()) return List.of();
        List<HinhAnhSanPhamEntity> list = new ArrayList<>();

        for (HinhAnhSanPhamCreateRequest r : reqs) {
            SanPhamEntity sp = sanPhamRepository.findById(r.getSanPhamId())
                    .orElseThrow(() -> new ApiException("Không tìm thấy sản phẩm ID=" + r.getSanPhamId()));
            MauSacEntity mau = null;
            if (r.getMauSacId() != null) {
                mau = mauSacRepository.findById(r.getMauSacId())
                        .orElseThrow(() -> new ApiException("Không tìm thấy màu ID=" + r.getMauSacId()));
            }

            HinhAnhSanPhamEntity e = HinhAnhSanPhamEntity.builder()
                    .sanPham(sp)
                    .mauSac(mau)
                    .tenTapTin(r.getTenTapTin())
                    .duongDan(r.getDuongDan())
                    .laAnhBia(Optional.ofNullable(r.getLaAnhBia()).orElse(false))
                    .trangThai(Optional.ofNullable(r.getTrangThai()).orElse(1))
                    .build();

            list.add(e);
        }

        // 🔹 Sau khi gom xong, đảm bảo mỗi màu chỉ có 1 ảnh bìa
        Map<String, List<HinhAnhSanPhamEntity>> grouped = list.stream()
                .collect(Collectors.groupingBy(e -> e.getSanPham().getId() + "-" +
                        (e.getMauSac() != null ? e.getMauSac().getId() : "null")));

        for (List<HinhAnhSanPhamEntity> group : grouped.values()) {
            boolean hasCover = group.stream().anyMatch(HinhAnhSanPhamEntity::getLaAnhBia);
            if (!hasCover && !group.isEmpty()) {
                group.get(0).setLaAnhBia(true); // auto chọn cái đầu làm bìa nếu chưa có
            }
        }

        return repo.saveAll(list).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<HinhAnhSanPhamDTO> updateMany(List<HinhAnhSanPhamUpdateRequest> reqs) {
        if (reqs == null || reqs.isEmpty()) return List.of();
        List<HinhAnhSanPhamEntity> updated = new ArrayList<>();

        for (HinhAnhSanPhamUpdateRequest r : reqs) {
            HinhAnhSanPhamEntity e = repo.findById(r.getId())
                    .orElseThrow(() -> new ApiException("Không tìm thấy ảnh ID=" + r.getId()));
            if (r.getDuongDan() != null) e.setDuongDan(r.getDuongDan());
            if (r.getTrangThai() != null) e.setTrangThai(r.getTrangThai());
            if (r.getLaAnhBia() != null) e.setLaAnhBia(r.getLaAnhBia());
            updated.add(e);
        }

        return repo.saveAll(updated).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public HinhAnhSanPhamDTO update(HinhAnhSanPhamUpdateRequest req) {
        HinhAnhSanPhamEntity e = repo.findById(req.getId())
                .orElseThrow(() -> new ApiException("Không tìm thấy hình ảnh ID=" + req.getId()));

        if (req.getDuongDan() != null) e.setDuongDan(req.getDuongDan());
        if (req.getTrangThai() != null) e.setTrangThai(req.getTrangThai());
        if (req.getLaAnhBia() != null) e.setLaAnhBia(req.getLaAnhBia());

        return toDTO(repo.save(e));
    }

    @Override
    public List<HinhAnhSanPhamDTO> getBySanPham(Long sanPhamId) {
        return repo.findBySanPham_Id(sanPhamId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<HinhAnhSanPhamDTO> getBySanPhamAndMau(Long sanPhamId, Long mauSacId) {
        return repo.findBySanPham_IdAndMauSac_Id(sanPhamId, mauSacId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

//    @Transactional
//    @Override
//    public void delete(Long id) {
//        if (!repo.existsById(id)) throw new ApiException("Ảnh không tồn tại!");
//        repo.deleteById(id);
//    }

    @Override
    @Transactional
    public HinhAnhSanPhamDTO createOne(Long sanPhamId, Long mauSacId, String tenTapTin, String duongDan) {
        SanPhamEntity sp = sanPhamRepository.findById(sanPhamId)
                .orElseThrow(() -> new ApiException("Không tìm thấy sản phẩm ID=" + sanPhamId));

        MauSacEntity mau = null;
        if (mauSacId != null) {
            mau = mauSacRepository.findById(mauSacId)
                    .orElseThrow(() -> new ApiException("Không tìm thấy màu ID=" + mauSacId));
        }

        HinhAnhSanPhamEntity e = HinhAnhSanPhamEntity.builder()
                .sanPham(sp)
                .mauSac(mau)
                .tenTapTin(tenTapTin)
                .duongDan(duongDan)
                .laAnhBia(false)
                .trangThai(1)
                .build();

        return toDTO(repo.save(e));
    }

    //Khi tick một ảnh làm ảnh bìa, tất cả ảnh khác cùng sanPham và mauSac sẽ bỏ cờ laAnhBia = false.
    @Transactional
    @Override
    public HinhAnhSanPhamDTO setCover(Long id) {
        HinhAnhSanPhamEntity target = repo.findById(id)
                .orElseThrow(() -> new ApiException("Không tìm thấy ảnh ID=" + id));

        Long spId = target.getSanPham().getId();
        Long mauId = target.getMauSac() != null ? target.getMauSac().getId() : null;

        // 🔸 Bỏ cờ bìa cũ cùng sản phẩm & màu
        repo.unsetAllCovers(spId, mauId);

        // 🔸 Gắn cờ bìa cho ảnh hiện tại
        repo.setCover(id);

        // 🔸 Cập nhật lại object trong bộ nhớ để trả đúng dữ liệu
        target.setLaAnhBia(true);

        return toDTO(target);
    }


    //Nếu ảnh bị xóa là ảnh bìa, tự động chọn ảnh khác cùng màu để làm ảnh bìa mới.
    @Transactional
    @Override
    public void delete(Long id) {
        HinhAnhSanPhamEntity e = repo.findById(id)
                .orElseThrow(() -> new ApiException("Ảnh không tồn tại"));

        boolean wasCover = Boolean.TRUE.equals(e.getLaAnhBia());
        Long spId = e.getSanPham().getId();
        Long mauId = e.getMauSac() != null ? e.getMauSac().getId() : null;

        repo.delete(e);

        if (wasCover && mauId != null) {
            repo.findBySanPham_IdAndMauSac_Id(spId, mauId)
                    .stream()
                    .findFirst()
                    .ifPresent(first -> {
                        first.setLaAnhBia(true);
                        repo.save(first);
                    });
        }
    }

    @Transactional
    @Override
    public List<HinhAnhSanPhamDTO> uploadImages(Long sanPhamId, Long mauSacId, List<MultipartFile> files, boolean laAnhBia) {
        if (files == null || files.isEmpty()) {
            throw new ApiException("Không có file nào để upload!");
        }

        // ✅ Nếu có ảnh bìa mới được chọn → hạ ảnh bìa cũ
        if (laAnhBia) {
            repo.unsetAllCovers(sanPhamId, mauSacId);
        }

        SanPhamEntity sp = sanPhamRepository.findById(sanPhamId)
                .orElseThrow(() -> new ApiException("Không tìm thấy sản phẩm ID=" + sanPhamId));

        MauSacEntity mau = null;
        if (mauSacId != null) {
            mau = mauSacRepository.findById(mauSacId)
                    .orElseThrow(() -> new ApiException("Không tìm thấy màu ID=" + mauSacId));
        }

        List<HinhAnhSanPhamEntity> savedList = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String duongDan = fileStorageService.saveProductImage(sanPhamId, file);

            HinhAnhSanPhamEntity entity = HinhAnhSanPhamEntity.builder()
                    .sanPham(sp)
                    .mauSac(mau)
                    .tenTapTin(file.getOriginalFilename())
                    .duongDan(duongDan)
                    .laAnhBia(laAnhBia && i == 0) // ✅ nếu chọn cover → ảnh đầu tiên là cover
                    .trangThai(1)
                    .build();

            savedList.add(repo.save(entity));
        }

        return savedList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
