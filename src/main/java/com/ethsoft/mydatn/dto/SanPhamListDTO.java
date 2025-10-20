package com.ethsoft.mydatn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SanPhamListDTO {
    private Long id;
    private String maSanPham;
    private String tenSanPham;
    private Integer trangThai;
    private LocalDateTime ngayTao;
    private Integer tongSoLuong; // SUM so_luong_ton từ SPCT (có thể null => 0)
}
