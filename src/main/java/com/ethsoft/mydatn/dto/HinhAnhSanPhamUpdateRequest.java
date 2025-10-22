package com.ethsoft.mydatn.dto;

import com.ethsoft.mydatn.entity.BaseAuditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HinhAnhSanPhamUpdateRequest extends BaseAuditable {
    private Long id;
    private String duongDan;
    private Boolean laAnhBia;
    private Integer trangThai;
}
