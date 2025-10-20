package com.ethsoft.mydatn.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "san_pham_mau_anh")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SanPhamMauAnhEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "duong_dan", nullable = false, length = 500)
    private String duongDan;

    @Column(name = "mo_ta", length = 255)
    private String moTa;

    @Column(name = "la_anh_bia", nullable = false)
    private Boolean laAnhBia = false;

    @Column(name = "thu_tu", nullable = false)
    private Integer thuTu = 0;

    @Column(name = "nguon", length = 50)
    private String nguon; // upload | clipboard | url

    // 🔗 Liên kết đến sản phẩm
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id", nullable = false)
    private SanPhamEntity sanPham;

    // 🔗 Liên kết đến màu sắc (chuẩn ORM)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mau_sac_id")
    private MauSacEntity mauSac;

    @Column(name = "ngay_tao", nullable = false)
    private LocalDateTime ngayTao = LocalDateTime.now();
}
