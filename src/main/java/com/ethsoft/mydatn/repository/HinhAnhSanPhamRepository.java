package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.HinhAnhSanPhamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HinhAnhSanPhamRepository extends JpaRepository<HinhAnhSanPhamEntity, Long> {
    List<HinhAnhSanPhamEntity> findBySanPham_Id(Long sanPhamId);
    List<HinhAnhSanPhamEntity> findBySanPham_IdAndMauSac_Id(Long sanPhamId, Long mauSacId);
    Optional<HinhAnhSanPhamEntity> findFirstBySanPham_IdAndMauSac_IdAndLaAnhBiaTrue(Long sanPhamId, Long mauSacId);
    // 🔸 Bỏ flag ảnh bìa cũ
    @Modifying
    @Query("UPDATE HinhAnhSanPhamEntity h SET h.laAnhBia = false WHERE h.sanPham.id = :spId AND (h.mauSac.id = :mauId OR (:mauId IS NULL AND h.mauSac.id IS NULL))")
    void unsetAllCovers(@Param("spId") Long sanPhamId, @Param("mauId") Long mauSacId);

    // 🔸 Đặt flag ảnh bìa mới
    @Modifying
    @Query("UPDATE HinhAnhSanPhamEntity h SET h.laAnhBia = true WHERE h.id = :id")
    void setCover(@Param("id") Long id);

    @Query("""
SELECT h FROM HinhAnhSanPhamEntity h
WHERE h.sanPham.id = :spId 
  AND (h.mauSac.id = :mauId OR (:mauId IS NULL AND h.mauSac IS NULL))
  AND h.laAnhBia = true
""")
    Optional<HinhAnhSanPhamEntity> findCoverBySanPhamAndMau(@Param("spId") Long sanPhamId,
                                                            @Param("mauId") Long mauSacId);

}
