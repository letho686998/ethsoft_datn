package com.ethsoft.mydatn.dto;

import com.ethsoft.mydatn.entity.BaseAuditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HinhAnhSanPhamCreateRequest extends BaseAuditable {
    private Long sanPhamId;
    private Long mauSacId;
    private String tenTapTin;
    private String duongDan;
    private Boolean laAnhBia;
    private Integer trangThai;
}
