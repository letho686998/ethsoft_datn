package com.ethsoft.mydatn.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HinhAnhSanPhamUpdateRequest {
    private Long id;
    private String duongDan;
    private Boolean laAnhBia;
    private Integer trangThai;
}
