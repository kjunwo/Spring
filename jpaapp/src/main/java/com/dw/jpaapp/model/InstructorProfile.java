package com.dw.jpaapp.model;

import com.dw.jpaapp.dto.InstructorProfileDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "instructor_profile")
public class InstructorProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bio", length = 3000) // length는 글자수(바이트수 아님)
    private String bio;

    @Column(name = "github_url")
    private String githubUrl;

    @OneToOne
    @JoinColumn(name = "instructor_id") // 단방향 참조
    private Instructor instructor;

    public InstructorProfileDTO toDTO() {
        return new InstructorProfileDTO(this.id, this.bio,
                this.githubUrl, this.instructor.getId());
    }
}
