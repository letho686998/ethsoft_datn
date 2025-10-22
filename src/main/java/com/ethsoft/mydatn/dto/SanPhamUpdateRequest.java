package com.ethsoft.mydatn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPhamUpdateRequest {
    private String tenSanPham;
    private String moTa;
    private Integer trangThai;
    private Long thuongHieuId;
    private Long danhMucId;
    private Long chatLieuId;
    private Long deGiayId;
    private Integer gioiTinh;
}
