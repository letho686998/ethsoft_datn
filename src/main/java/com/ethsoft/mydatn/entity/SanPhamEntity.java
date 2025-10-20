// src/main/java/com/ethsoft/mydatn/entity/SanPhamEntity.java
package com.ethsoft.mydatn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "san_pham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SanPhamEntity extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_san_pham", unique = true, length = 50)
    private String maSanPham;

    @Column(name = "ten_san_pham", nullable = false, length = 255)
    private String tenSanPham;

    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    // 1 = Đang kinh doanh, 0 = Ngừng
    @Column(name = "trang_thai")
    private Integer trangThai = 1;

    // ==========================
    // 🔗 Quan hệ đến các bảng khác
    // ==========================

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private java.util.List<SanPhamChiTietEntity> chiTietList = new java.util.ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thuong_hieu_id", foreignKey = @ForeignKey(name = "fk_san_pham_thuong_hieu"))
    private ThuongHieuEntity thuongHieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "danh_muc_id", foreignKey = @ForeignKey(name = "fk_san_pham_danh_muc"))
    private DanhMucEntity danhMuc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_lieu_id", foreignKey = @ForeignKey(name = "fk_san_pham_chat_lieu"))
    private ChatLieuEntity chatLieu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "de_giay_id", foreignKey = @ForeignKey(name = "fk_san_pham_de_giay"))
    private DeGiayEntity deGiay;

    // 0 = Nam, 1 = Nữ, 2 = Cả hai
    @Column(name = "gioi_tinh")
    private Integer gioiTinh = 2;
}
