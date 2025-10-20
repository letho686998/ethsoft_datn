// src/main/java/com/ethsoft/mydatn/dto/SanPhamMauAnhDTO.java
package com.ethsoft.mydatn.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SanPhamMauAnhDTO {
    private Long id;
    private Long sanPhamId;
    private Long mauSacId;  // optional
    private String duongDan;
    private String moTa;
    private Boolean laAnhBia;
    private Integer thuTu;
    private String nguon;   // upload|clipboard|url
}
