package com.ethsoft.mydatn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "kich_co")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class KichCoEntity extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_kich_co", nullable = false, unique = true, length = 50)
    private String tenKichCo;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai = 1;

    @OneToMany(mappedBy = "kichCo", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<SanPhamChiTietEntity> sanPhamChiTiets;

}
