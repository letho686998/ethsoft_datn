package com.ethsoft.mydatn.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatLieuDTO {
    private Long id;
    private String tenChatLieu;
    private Integer trangThai;
    private LocalDateTime ngayTao;
}
