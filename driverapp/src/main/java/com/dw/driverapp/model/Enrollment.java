package com.dw.driverapp.model;

import com.dw.driverapp.dto.EnrollmentDTO;
import com.dw.driverapp.dto.SubjectEnrollmentDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name="수강신청")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="user_name")
    private User user;
    @ManyToOne
    @JoinColumn(name="subject_id")
    private Subject subject;
    @Column(name="purchase_time")
    private LocalDateTime purchaseTime;
    @Column(name = "completed", nullable = false)
    private boolean completed;

    public void completeEnrollment() {
        this.completed = true;
    }

    public EnrollmentDTO TOdto(){
        EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
        enrollmentDTO.setUsername(this.user.getUserName());
        enrollmentDTO.setSubjectName(this.subject.getTitle());
        enrollmentDTO.setPrice(this.subject.getPrice());
        return enrollmentDTO;
    }
    public SubjectEnrollmentDTO toDto(){
        SubjectEnrollmentDTO subjectEnrollmentDTO = new SubjectEnrollmentDTO();
        subjectEnrollmentDTO.setId(this.subject.getId());
        subjectEnrollmentDTO.setUsername(this.user.getUserName());
        subjectEnrollmentDTO.setTitle(this.subject.getTitle());
        subjectEnrollmentDTO.setCompletionStatus(this.completed ? "완료" : "미완료");
        return subjectEnrollmentDTO;
    }
}
