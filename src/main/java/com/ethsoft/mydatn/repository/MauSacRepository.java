package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.MauSacEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MauSacRepository extends JpaRepository<MauSacEntity, Long> {
    boolean existsByTenMau(String tenMau);
    boolean existsByMaMau(String maMau);
}
