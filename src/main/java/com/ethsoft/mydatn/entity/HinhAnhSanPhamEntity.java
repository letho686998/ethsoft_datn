package com.ethsoft.mydatn.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hinh_anh_san_pham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HinhAnhSanPhamEntity extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Liên kết đến sản phẩm
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id", nullable = false, foreignKey = @ForeignKey(name = "fk_hinhanh_sanpham"))
    private SanPhamEntity sanPham;

    // 🔗 Liên kết đến màu sắc (nếu có)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mau_sac_id", nullable = true, foreignKey = @ForeignKey(name = "fk_hinhanh_mau"))
    private MauSacEntity mauSac;

    @Column(name = "ten_tap_tin", nullable = false)
    private String tenTapTin;

    @Column(name = "duong_dan", columnDefinition = "NVARCHAR(500)")
    private String duongDan;

    @Column(name = "la_anh_bia")
    private Boolean laAnhBia = false;

    @Column(name = "trang_thai")
    private Integer trangThai = 1;
}
