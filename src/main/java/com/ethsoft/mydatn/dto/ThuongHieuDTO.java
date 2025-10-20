package com.ethsoft.mydatn.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ThuongHieuDTO {
    private Long id;
    private String tenThuongHieu;
    private Integer trangThai;
    private LocalDateTime ngayTao;
}
