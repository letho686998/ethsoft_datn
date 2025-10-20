package com.ethsoft.mydatn.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "mau_sac")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MauSacEntity extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_mau", nullable = false, unique = true, length = 100)
    private String tenMau;

    @Column(name = "ma_mau", nullable = false, length = 20)
    private String maMau; // ví dụ: #FF5733

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai = 1;

    // 🔁 Liên kết ngược với ảnh
    @OneToMany(mappedBy = "mauSac", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<SanPhamMauAnhEntity> mauAnhList;

    // 🔁 Liên kết ngược với SPCT
    @OneToMany(mappedBy = "mauSac", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<SanPhamChiTietEntity> sanPhamChiTiets;
}
