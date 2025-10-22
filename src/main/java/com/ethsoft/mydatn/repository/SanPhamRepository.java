package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.SanPhamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SanPhamRepository extends JpaRepository<SanPhamEntity, Long> {
    @Query("SELECT MAX(sp.maSanPham) FROM SanPhamEntity sp")
    String findMaxMaSanPham();

    boolean existsByMaSanPham(String maSanPham);
}

