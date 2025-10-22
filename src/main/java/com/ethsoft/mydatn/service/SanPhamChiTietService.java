// src/main/java/com/ethsoft/mydatn/service/SanPhamChiTietService.java
package com.ethsoft.mydatn.service;

import com.ethsoft.mydatn.dto.SanPhamChiTietCreateRequest;
import com.ethsoft.mydatn.dto.SanPhamChiTietDTO;
import com.ethsoft.mydatn.dto.SanPhamChiTietUpdateRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SanPhamChiTietService {
    List<SanPhamChiTietDTO> createMany(List<SanPhamChiTietCreateRequest> reqs);
    List<SanPhamChiTietDTO> updateMany(List<SanPhamChiTietUpdateRequest> reqs);
    List<SanPhamChiTietDTO> getBySanPham(Long sanPhamId);
    List<SanPhamChiTietDTO> getAll();
}

