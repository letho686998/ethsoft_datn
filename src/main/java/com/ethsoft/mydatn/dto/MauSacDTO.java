package com.ethsoft.mydatn.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MauSacDTO {
    private Long id;
    private String tenMau;
    private String maMau;
    private Integer trangThai;
    private LocalDateTime ngayTao;
}
