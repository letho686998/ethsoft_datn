package com.ethsoft.mydatn.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DanhMucDTO {
    private Long id;
    private String tenDanhMuc;
    private Integer trangThai;
    private LocalDateTime ngayTao;
}
