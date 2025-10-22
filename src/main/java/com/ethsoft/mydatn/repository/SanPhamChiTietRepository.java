// src/main/java/com/ethsoft/mydatn/repository/SanPhamChiTietRepository.java
package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.SanPhamChiTietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTietEntity, Long> {
    List<SanPhamChiTietEntity> findBySanPham_Id(Long sanPhamId);
    Optional<SanPhamChiTietEntity> findBySanPham_IdAndMauSac_IdAndKichCo_Id(Long sanPhamId, Long mauSacId, Long kichCoId);
    long countBySanPham_IdAndTrangThai(Long sanPhamId, int trangThai);

}
