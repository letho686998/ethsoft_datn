// src/main/java/com/ethsoft/mydatn/dto/SanPhamChiTietCreateRequest.java
package com.ethsoft.mydatn.dto;

import com.ethsoft.mydatn.entity.BaseAuditable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SanPhamChiTietCreateRequest extends BaseAuditable {
    private Long sanPhamId;
    private Long mauSacId;
    private Long kichCoId;
    private Integer soLuongTon;
    private BigDecimal giaBan;
    private Integer gioiTinh;   // 0=Nam,1=Nữ,2=Unisex
    private Integer trangThai;  // 1=KD,0=Ngừng
}
