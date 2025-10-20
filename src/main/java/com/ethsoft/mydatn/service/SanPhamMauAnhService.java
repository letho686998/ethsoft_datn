// src/main/java/com/ethsoft/mydatn/service/SanPhamMauAnhService.java
package com.ethsoft.mydatn.service;

import com.ethsoft.mydatn.dto.SanPhamMauAnhCreateRequest;
import com.ethsoft.mydatn.dto.SanPhamMauAnhDTO;

import java.util.List;

public interface SanPhamMauAnhService {
    List<SanPhamMauAnhDTO> saveByColor(Long sanPhamId, Long mauSacId, List<SanPhamMauAnhCreateRequest> reqs);
    List<SanPhamMauAnhDTO> listByColor(Long sanPhamId, Long mauSacId);
    List<SanPhamMauAnhDTO> listByProduct(Long sanPhamId);
}
