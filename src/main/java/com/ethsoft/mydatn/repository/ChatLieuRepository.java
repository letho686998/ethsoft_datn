package com.ethsoft.mydatn.repository;

import com.ethsoft.mydatn.entity.ChatLieuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLieuRepository extends JpaRepository<ChatLieuEntity, Long> {
    boolean existsByTenChatLieu(String tenChatLieu);
}
