package com.dw.driverapp.model;

import com.dw.driverapp.dto.UserDTO;
import com.dw.driverapp.dto.UserPointDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name="사용자")
public class User {
    @Id
    @Column(name="user_name")
    private String userName;
    @Setter
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="email", nullable = false, unique = true)
    private String email;
    @Column(name = "real_name", nullable = false)
    private String realName;
    @Column(name="birthdate",nullable = false, unique = true)
    private LocalDate birthdate;
    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;
    @Column(name="created_at", updatable = false)
    private LocalDate createdAt;
    @Column(name="point")
    private int point;

    public UserDTO toDTO(){
        return new UserDTO(
                this.userName,
                null,
                this.email,
                this.realName,
                this.birthdate,
                authority.getAuthorityName(),
                this.point
        );
    }
    public UserPointDTO todto(){
        return new UserPointDTO(
                this.userName,
                this.point
        );
    }
}