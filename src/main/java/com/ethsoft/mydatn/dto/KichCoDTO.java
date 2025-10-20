package com.ethsoft.mydatn.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class KichCoDTO {
    private Long id;
    private String tenKichCo;
    private Integer trangThai;
    private LocalDateTime ngayTao;
}
