// src/main/java/com/ethsoft/mydatn/service/SanPhamService.java
package com.ethsoft.mydatn.service;

import com.ethsoft.mydatn.dto.*;

import java.util.List;

public interface SanPhamService {
    SanPhamDTO create(SanPhamCreateRequest req);
    SanPhamDTO update(Long id, SanPhamUpdateRequest req);
    SanPhamDTO getById(Long id);
    List<SanPhamDTO> getAllForList();
}
