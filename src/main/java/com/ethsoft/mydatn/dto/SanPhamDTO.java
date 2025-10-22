// src/main/java/com/ethsoft/mydatn/dto/SanPhamDTO.java
package com.ethsoft.mydatn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SanPhamDTO  {
    private Long id;
    private String maSanPham;
    private String tenSanPham;
    private String moTa;
    private Integer trangThai;
    private Long thuongHieuId;
    private Long danhMucId;
    private Long chatLieuId;
    private Long deGiayId;
    private Integer gioiTinh;

    private String nguoiTao;
    private String nguoiCapNhat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime ngayTao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private LocalDateTime ngayCapNhat;

    // Phục vụ danh sách
    private Integer tongSoLuong;
    private String tenThuongHieu; // nếu bạn có lookup
    private String tenDanhMuc;    // nếu bạn có lookup
    private String tenChatLieu;
    private String tenDeGiay;
}
