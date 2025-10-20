// src/main/java/com/ethsoft/mydatn/dto/SanPhamChiTietCreateRequest.java
package com.ethsoft.mydatn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SanPhamChiTietRequest {
    private Long id; // ✅ Dùng cho update (null nếu là create)
    private Long sanPhamId;
    private Long mauSacId;
    private Long kichCoId;
    private Integer soLuongTon;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private Integer trangThai; // 1 = Đang KD, 0 = Ngừng

    // ✅ Thêm 2 trường audit do FE gửi lên
    private String nguoiCapNhat;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime ngayCapNhat;
}
