package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.ThuongHieuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThuongHieuRepository extends JpaRepository<ThuongHieuEntity, Long> {
    boolean existsByTenThuongHieu(String tenThuongHieu);
}
