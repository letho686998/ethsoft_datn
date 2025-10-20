// src/main/java/com/ethsoft/mydatn/dto/SanPhamMauAnhCreateRequest.java
package com.ethsoft.mydatn.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SanPhamMauAnhCreateRequest {
    private String duongDan;  // URL public sau khi ghi file
    private String moTa;
    private Boolean laAnhBia;
    private Integer thuTu;
    private String nguon;     // upload|clipboard|url
}
