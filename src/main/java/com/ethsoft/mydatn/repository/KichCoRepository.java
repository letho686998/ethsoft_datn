package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.KichCoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KichCoRepository extends JpaRepository<KichCoEntity, Long> {
    boolean existsByTenKichCo(String tenKichCo);
}
