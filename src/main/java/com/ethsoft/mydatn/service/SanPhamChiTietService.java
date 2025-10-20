// src/main/java/com/ethsoft/mydatn/service/SanPhamChiTietService.java
package com.ethsoft.mydatn.service;

import com.ethsoft.mydatn.dto.SanPhamChiTietRequest;
import com.ethsoft.mydatn.dto.SanPhamChiTietDTO;

import java.util.List;

public interface SanPhamChiTietService {
    List<SanPhamChiTietDTO> bulkCreate(List<SanPhamChiTietRequest> reqs);
    List<SanPhamChiTietDTO> bulkUpdate(List<SanPhamChiTietRequest> reqs);
    List<SanPhamChiTietDTO> getBySanPham(Long sanPhamId);
    List<SanPhamChiTietDTO> getAll();
}
