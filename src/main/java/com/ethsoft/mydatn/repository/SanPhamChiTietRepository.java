// src/main/java/com/ethsoft/mydatn/repository/SanPhamChiTietRepository.java
package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.SanPhamChiTietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SanPhamChiTietRepository extends JpaRepository<SanPhamChiTietEntity, Long> {
    List<SanPhamChiTietEntity> findBySanPham_Id(Long sanPhamId);

    // 🔹 Join với bảng ảnh để lấy ảnh bìa theo màu sắc tương ứng
//    @Query("""
//            SELECT spct.id, spct.sanPham.id, spct.mauSac.id, spct.kichCo.id,
//                   spct.maSpct, spct.giaNhap, spct.giaBan, spct.soLuongTon, spct.trangThai,
//                   sp.maSanPham, sp.tenSanPham, ms.tenMau, kc.tenKichCo,
//                   ma.duongDan AS anhBiaUrl
//            FROM SanPhamChiTietEntity spct
//            LEFT JOIN spct.sanPham sp
//            LEFT JOIN spct.mauSac ms
//            LEFT JOIN spct.kichCo kc
//            LEFT JOIN SanPhamMauAnhEntity ma
//                   ON ma.sanPham.id = sp.id
//                  AND ma.mauSac.id = ms.id
//                  AND ma.laAnhBia = true
//            """)
//    List<Object[]> findAllWithAnhBia();

}
