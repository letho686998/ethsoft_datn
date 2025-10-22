package com.ethsoft.mydatn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HinhAnhSanPhamDTO {
    private Long id;
    private Long sanPhamId;
    private Long mauSacId;
    private String tenTapTin;
    private String duongDan;
    private Boolean laAnhBia;
    private Integer trangThai;

    private String tenMau;
    private String maSanPham;
    private String tenSanPham;

    private String nguoiTao;
    private String nguoiCapNhat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime ngayTao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime ngayCapNhat;
}
