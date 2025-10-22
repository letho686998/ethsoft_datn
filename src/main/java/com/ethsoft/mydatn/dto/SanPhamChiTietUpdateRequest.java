package com.ethsoft.mydatn.dto;

import com.ethsoft.mydatn.entity.BaseAuditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor   // ✅ BẮT BUỘC có để Jackson khởi tạo object khi parse JSON
@AllArgsConstructor
public class SanPhamChiTietUpdateRequest extends BaseAuditable {
    private Long id;
    private Integer soLuongTon;
    private BigDecimal giaBan;
    private Integer gioiTinh;
    private Integer trangThai;
}
