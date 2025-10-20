package com.ethsoft.mydatn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "de_giay")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DeGiayEntity extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_de_giay", nullable = false, unique = true, length = 255)
    private String tenDeGiay;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai = 1;

    @OneToMany(mappedBy = "deGiay", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<SanPhamEntity> sanPhams;
}
