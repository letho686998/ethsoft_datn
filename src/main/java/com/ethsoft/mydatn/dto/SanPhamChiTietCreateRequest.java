// src/main/java/com/ethsoft/mydatn/dto/SanPhamChiTietCreateRequest.java
package com.ethsoft.mydatn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SanPhamChiTietCreateRequest {
    private Long sanPhamId;
    private Long mauSacId;
    private Long kichCoId;
    private Integer soLuongTon;
    private BigDecimal giaBan;
    private Integer gioiTinh;   // 0=Nam,1=Nữ,2=Unisex
    private Integer trangThai;  // 1=KD,0=Ngừng
}
