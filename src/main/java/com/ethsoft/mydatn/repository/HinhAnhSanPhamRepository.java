package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.HinhAnhSanPhamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HinhAnhSanPhamRepository extends JpaRepository<HinhAnhSanPhamEntity, Long> {
    List<HinhAnhSanPhamEntity> findBySanPham_Id(Long sanPhamId);
    List<HinhAnhSanPhamEntity> findBySanPham_IdAndMauSac_Id(Long sanPhamId, Long mauSacId);
    Optional<HinhAnhSanPhamEntity> findFirstBySanPham_IdAndMauSac_IdAndLaAnhBiaTrue(Long sanPhamId, Long mauSacId);
}
