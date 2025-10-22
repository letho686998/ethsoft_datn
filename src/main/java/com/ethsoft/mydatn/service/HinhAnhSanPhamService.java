package com.ethsoft.mydatn.service;

import com.ethsoft.mydatn.dto.*;
import java.util.List;

public interface HinhAnhSanPhamService {
    HinhAnhSanPhamDTO createOne(Long sanPhamId, Long mauSacId, String tenTapTin, String duongDan);
    List<HinhAnhSanPhamDTO> createMany(List<HinhAnhSanPhamCreateRequest> reqs);
    HinhAnhSanPhamDTO update(HinhAnhSanPhamUpdateRequest req);
    List<HinhAnhSanPhamDTO> updateMany(List<HinhAnhSanPhamUpdateRequest> reqs);
    HinhAnhSanPhamDTO setCover(Long id); // ✅ Mới
    void delete(Long id); // ✅ Bổ sung logic khi xóa ảnh bìa
    List<HinhAnhSanPhamDTO> getBySanPham(Long sanPhamId);
    List<HinhAnhSanPhamDTO> getBySanPhamAndMau(Long sanPhamId, Long mauSacId);
}

