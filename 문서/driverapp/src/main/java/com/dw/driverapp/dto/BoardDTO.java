package com.dw.driverapp.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate = LocalDateTime.now();
    private List<Long> commentList = new ArrayList<>();
    private List<String> commentList1 = new ArrayList<>();
    private List<String> commentList2 = new ArrayList<>();
}
