package com.ethsoft.mydatn.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BaseDanhMucDTO {
    private Long id;
    private String ten;
    private Integer trangThai;
    private LocalDateTime ngayTao;
    private LocalDateTime ngayCapNhat;
}
