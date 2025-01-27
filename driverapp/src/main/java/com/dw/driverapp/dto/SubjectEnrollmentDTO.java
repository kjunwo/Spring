package com.dw.driverapp.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SubjectEnrollmentDTO {
    private Long id;
    private String username;
    private String title;
    private String completionStatus;
}
