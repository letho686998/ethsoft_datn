package com.ethsoft.mydatn.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DeGiayDTO {
    private Long id;
    private String tenDeGiay;
    private Integer trangThai;
    private LocalDateTime ngayTao;
}
