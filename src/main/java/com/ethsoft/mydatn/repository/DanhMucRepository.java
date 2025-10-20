package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.DanhMucEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DanhMucRepository extends JpaRepository<DanhMucEntity, Long> {
    boolean existsByTenDanhMuc(String tenDanhMuc);
}
