package com.ethsoft.mydatn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chat_lieu")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatLieuEntity extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_chat_lieu", nullable = false, unique = true, length = 255)
    private String tenChatLieu;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai = 1;

    @OneToMany(mappedBy = "chatLieu", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<SanPhamEntity> sanPhams;
}
