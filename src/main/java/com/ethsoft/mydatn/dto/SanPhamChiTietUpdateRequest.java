package com.ethsoft.mydatn.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor   // ✅ BẮT BUỘC có để Jackson khởi tạo object khi parse JSON
@AllArgsConstructor
public class SanPhamChiTietUpdateRequest {
    private Long id;
    private Integer soLuongTon;
    private BigDecimal giaBan;
    private Integer gioiTinh;
    private Integer trangThai;
}
