// src/main/java/com/ethsoft/mydatn/repository/SanPhamMauAnhRepository.java
package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.SanPhamMauAnhEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SanPhamMauAnhRepository extends JpaRepository<SanPhamMauAnhEntity, Long> {
    List<SanPhamMauAnhEntity> findBySanPham_IdAndMauSac_IdOrderByThuTuAsc(Long sanPhamId, Long mauSacId);
    List<SanPhamMauAnhEntity> findBySanPham_IdOrderByThuTuAsc(Long sanPhamId);
    // SanPhamMauAnhRepository.java
    Optional<SanPhamMauAnhEntity> findFirstBySanPham_IdAndMauSac_IdAndLaAnhBiaTrue(Long sanPhamId, Long mauSacId);


    // Reset cover theo nhóm (sản phẩm + màu nếu có)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           UPDATE SanPhamMauAnhEntity e
           SET e.laAnhBia = false
           WHERE e.sanPham.id = :sanPhamId
             AND (:mauSacId IS NULL OR e.mauSac.id = :mauSacId)
           """)
    int resetCover(@Param("sanPhamId") Long sanPhamId, @Param("mauSacId") Long mauSacId);

    // Ghim cover theo id ảnh
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE SanPhamMauAnhEntity e SET e.laAnhBia = true WHERE e.id = :id")
    int markCover(@Param("id") Long id);
}
