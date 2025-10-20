package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.DeGiayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeGiayRepository extends JpaRepository<DeGiayEntity, Long> {
    boolean existsByTenDeGiay(String tenDeGiay);
}
