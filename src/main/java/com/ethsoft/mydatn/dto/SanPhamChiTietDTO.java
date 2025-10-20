// src/main/java/com/ethsoft/mydatn/dto/SanPhamChiTietDTO.java
package com.ethsoft.mydatn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class SanPhamChiTietDTO {
    private Long id;
    private Long sanPhamId;
    private String maSpct;
    private Long mauSacId;
    private Long kichCoId;
    private Integer soLuongTon;
    private BigDecimal giaNhap;
    private BigDecimal giaBan;
    private Integer trangThai;

    // ✅ Bổ sung thêm các trường phục vụ hiển thị bảng 11 cột
    private String maSanPham;
    private String tenSanPham;
    private String tenMau;
    private String tenKichCo;
    private String anhBiaUrl;

    private String nguoiTao;
    private String nguoiCapNhat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime ngayTao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime ngayCapNhat;
}
