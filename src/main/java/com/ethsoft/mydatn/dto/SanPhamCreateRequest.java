// src/main/java/com/ethsoft/mydatn/dto/SanPhamCreateRequest.java
package com.ethsoft.mydatn.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SanPhamCreateRequest {
    private String tenSanPham;
    private String moTa;
    private Integer trangThai;   // default 1
    private Long thuongHieuId;
    private Long danhMucId;
    private Long chatLieuId;
    private Long deGiayId;
    private Integer gioiTinh;    // 0/1/2
}

