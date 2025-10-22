package com.ethsoft.mydatn.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HinhAnhSanPhamCreateRequest {
    private Long sanPhamId;
    private Long mauSacId;
    private String tenTapTin;
    private String duongDan;
    private Boolean laAnhBia;
    private Integer trangThai;
}
