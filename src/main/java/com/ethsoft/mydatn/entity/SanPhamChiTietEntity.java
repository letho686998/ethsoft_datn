package com.ethsoft.mydatn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "san_pham_chi_tiet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamChiTietEntity extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ví dụ: SP000123-Red-42
    @Column(name = "ma_spct", length = 100)
    private String maSpct;

    // 🔗 Liên kết đến sản phẩm chính
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id", nullable = false)
    private SanPhamEntity sanPham;

    // 🔗 Liên kết đến màu sắc (chuẩn ORM)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mau_sac_id", nullable = false)
    private MauSacEntity mauSac;

    // 🔗 Liên kết đến kích cỡ (chuẩn ORM)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kich_co_id", nullable = false)
    private KichCoEntity kichCo;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

//    @Column(name = "gia_nhap", precision = 18, scale = 2)
//    private BigDecimal giaNhap;

    @Column(name = "gia_ban", precision = 18, scale = 2)
    private BigDecimal giaBan;

    // 1=Đang bán, 0=Ngừng
    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "gioi_tinh")
    private Integer gioiTinh; // 0=Nam, 1=Nữ, 2=Unisex
}
