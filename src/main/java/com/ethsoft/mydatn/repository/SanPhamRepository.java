package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.SanPhamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SanPhamRepository extends JpaRepository<SanPhamEntity, Long> {

    @Query("""
        SELECT sp.id AS id,
               sp.maSanPham AS maSanPham,
               sp.tenSanPham AS tenSanPham,
               sp.trangThai AS trangThai,
               sp.ngayTao AS ngayTao,
               sp.thuongHieu.id AS thuongHieuId,
               sp.danhMuc.id AS danhMucId,
               COALESCE(SUM(spct.soLuongTon), 0) AS tongSoLuong
        FROM SanPhamEntity sp
        LEFT JOIN sp.chiTietList spct
        GROUP BY sp.id, sp.maSanPham, sp.tenSanPham, sp.trangThai, sp.ngayTao, sp.thuongHieu.id, sp.danhMuc.id
        ORDER BY sp.id DESC
    """)
    List<Object[]> findAllForList();

    @Query("SELECT MAX(sp.maSanPham) FROM SanPhamEntity sp")
    String findMaxMaSanPham();


    boolean existsByMaSanPham(String maSanPham);
}
