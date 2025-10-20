package com.ethsoft.mydatn.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties({"sanPhams", "hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "thuong_hieu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThuongHieuEntity extends BaseAuditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_thuong_hieu", nullable = false, unique = true, length = 255)
    private String tenThuongHieu;

    @Column(name = "trang_thai", nullable = false)
    private Integer trangThai = 1; // 1=Hoạt động, 0=Ngừng

    @OneToMany(mappedBy = "thuongHieu", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<SanPhamEntity> sanPhams;
}
