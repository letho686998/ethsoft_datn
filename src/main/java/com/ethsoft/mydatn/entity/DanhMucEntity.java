package com.ethsoft.mydatn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "danh_muc")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DanhMucEntity extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_danh_muc", nullable = false, unique = true, length = 255)
    private String tenDanhMuc;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai = 1;

    @OneToMany(mappedBy = "danhMuc", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<SanPhamEntity> sanPhams;
}
