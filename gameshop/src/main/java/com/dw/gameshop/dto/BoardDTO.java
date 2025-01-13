package com.dw.gameshop.dto;

import com.dw.gameshop.model.Board;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime modifiedDate;
}
