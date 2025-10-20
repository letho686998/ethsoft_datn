// src/main/java/com/ethsoft/mydatn/controller/api/SanPhamMauAnhRestController.java
package com.ethsoft.mydatn.controller.api;

import com.ethsoft.mydatn.entity.SanPhamMauAnhEntity;
import com.ethsoft.mydatn.repository.MauSacRepository;
import com.ethsoft.mydatn.repository.SanPhamMauAnhRepository;
import com.ethsoft.mydatn.repository.SanPhamRepository;
import com.ethsoft.mydatn.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/san-pham-mau-anh")
@RequiredArgsConstructor
public class SanPhamMauAnhRestController {

    private final SanPhamRepository sanPhamRepository;
    private final MauSacRepository mauSacRepository;
    private final SanPhamMauAnhRepository spmaRepository;
    private final FileStorageService storage;

    // Upload 1 ảnh
    @PostMapping("/upload")
    public ResponseEntity<?> uploadOne(
            @RequestParam Long sanPhamId,
            @RequestParam(required = false) Long mauSacId,
            @RequestParam MultipartFile file,
            @RequestParam(defaultValue = "false") boolean laAnhBia,
            @RequestParam(defaultValue = "0") int thuTu
    ) {
        String url = storage.saveProductImage(sanPhamId, file);

        SanPhamMauAnhEntity e = SanPhamMauAnhEntity.builder()
                .sanPham(sanPhamRepository.getReferenceById(sanPhamId))
                .mauSac(mauSacId != null ? mauSacRepository.findById(mauSacId).orElse(null) : null)
                .duongDan(url)
                .moTa(null)
                .laAnhBia(laAnhBia)
                .thuTu(thuTu)
                .nguon("upload")
                .ngayTao(LocalDateTime.now())
                .build();
        spmaRepository.save(e);

        return ResponseEntity.ok(Map.of(
                "id", e.getId(),
                "duongDan", e.getDuongDan()
        ));
    }

    // Upload nhiều ảnh (key = files)
    @PostMapping("/upload-multi")
    @Transactional
    public ResponseEntity<?> uploadMulti(
            @RequestParam Long sanPhamId,
            @RequestParam(required = false) Long mauSacId,
            @RequestParam("files") List<MultipartFile> files
    ) {
        List<Map<String, Object>> out = new ArrayList<>();
        int idx = 0;
        for (MultipartFile f : files) {
            String url = storage.saveProductImage(sanPhamId, f);
            SanPhamMauAnhEntity e = SanPhamMauAnhEntity.builder()
                    .sanPham(sanPhamRepository.getReferenceById(sanPhamId))
                    .mauSac(mauSacId != null ? mauSacRepository.findById(mauSacId).orElse(null) : null)
                    .duongDan(url)
                    .laAnhBia(false)
                    .thuTu(idx++)
                    .nguon("upload")
                    .ngayTao(LocalDateTime.now())
                    .build();
            spmaRepository.save(e);
            out.add(Map.of("id", e.getId(), "duongDan", e.getDuongDan()));
        }
        return ResponseEntity.ok(out);
    }

    // Danh sách ảnh theo sản phẩm (option theo màu)
    @GetMapping("/by-san-pham/{sanPhamId}")
    public ResponseEntity<?> list(
            @PathVariable Long sanPhamId,
            @RequestParam(required = false) Long mauSacId
    ) {
        List<SanPhamMauAnhEntity> list = (mauSacId != null)
                ? spmaRepository.findBySanPham_IdAndMauSac_IdOrderByThuTuAsc(sanPhamId, mauSacId)
                : spmaRepository.findBySanPham_IdOrderByThuTuAsc(sanPhamId);
        return ResponseEntity.ok(list.stream().map(e -> Map.of(
                "id", e.getId(),
                "duongDan", e.getDuongDan(),
                "laAnhBia", e.getLaAnhBia(),
                "thuTu", e.getThuTu()
        )));
    }

    // Xóa ảnh
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        spmaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Đặt ảnh bìa
    @PutMapping("/{id}/cover")
    @Transactional
    public ResponseEntity<?> setCover(@PathVariable Long id) {
        SanPhamMauAnhEntity cover = spmaRepository.findById(id).orElseThrow();

        Long sanPhamId = cover.getSanPham().getId();
        Long mauSacId = (cover.getMauSac() != null) ? cover.getMauSac().getId() : null;

        int reset = spmaRepository.resetCover(sanPhamId, mauSacId);
        int setOne = spmaRepository.markCover(id);

        // (Tuỳ chọn) kiểm tra số dòng ảnh hưởng
        if (setOne != 1) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Không thể đặt ảnh bìa", "updatedRows", setOne));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Đã đặt ảnh bìa",
                "resetCount", reset,
                "markedId", id
        ));
    }
//    public ResponseEntity<?> setCover(@PathVariable Long id) {
//        SanPhamMauAnhEntity cover = spmaRepository.findById(id).orElseThrow();
//        // bỏ cờ bìa với cùng sản phẩm (+ cùng màu nếu có)
//        List<SanPhamMauAnhEntity> sameGroup = (cover.getMauSac() != null)
//                ? spmaRepository.findBySanPham_IdAndMauSac_IdOrderByThuTuAsc(
//                cover.getSanPham().getId(), cover.getMauSac().getId())
//                : spmaRepository.findBySanPham_IdOrderByThuTuAsc(cover.getSanPham().getId());
//        sameGroup.forEach(e -> e.setLaAnhBia(Objects.equals(e.getId(), id)));
//        spmaRepository.saveAll(sameGroup);
//        return ResponseEntity.ok().build();
//    }
    // ============================================================
// 🟩 ĐẶT ẢNH BÌA THEO BODY JSON (API: /api/san-pham-mau-anh/set-cover)
// ============================================================
//    @PutMapping("/set-cover")
//    @Transactional
//    public ResponseEntity<?> setCoverByBody(@RequestBody Map<String, Object> body) {
//        try {
//            // 🔹 Lấy dữ liệu từ body
//            Long sanPhamId = ((Number) body.get("sanPhamId")).longValue();
//            Long mauSacId = body.get("mauSacId") != null
//                    ? ((Number) body.get("mauSacId")).longValue()
//                    : null;
//            String duongDan = (String) body.get("duongDan");
//
//            // 🔹 Lấy danh sách ảnh cùng nhóm
//            List<SanPhamMauAnhEntity> group = (mauSacId != null)
//                    ? spmaRepository.findBySanPham_IdAndMauSac_IdOrderByThuTuAsc(sanPhamId, mauSacId)
//                    : spmaRepository.findBySanPham_IdOrderByThuTuAsc(sanPhamId);
//
//            if (group.isEmpty()) {
//                return ResponseEntity.badRequest().body(Map.of("error", "Không tìm thấy ảnh của sản phẩm này"));
//            }
//
//            // 🔹 Reset ảnh bìa cũ & set ảnh mới
//            group.forEach(e -> e.setLaAnhBia(
//                    e.getDuongDan() != null && e.getDuongDan().equals(duongDan)
//            ));
//            spmaRepository.saveAll(group);
//
//            return ResponseEntity.ok(Map.of(
//                    "message", "Đã đặt ảnh bìa thành công!",
//                    "sanPhamId", sanPhamId,
//                    "mauSacId", mauSacId,
//                    "duongDan", duongDan
//            ));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.internalServerError()
//                    .body(Map.of("error", "Lỗi khi cập nhật ảnh bìa!", "details", e.getMessage()));
//        }
//    }

}
