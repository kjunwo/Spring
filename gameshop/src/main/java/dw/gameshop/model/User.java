package dw.gameshop.model;

import dw.gameshop.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
// @Setter 가 없는 이유는 생성시점에만 만들고 수정을 못하게 하려고 넣지 않음
@ToString
@Entity
@Table(name="user")
public class User {
    @Id
    @Column(name="user_name")
    private String userName;
    @Column(name="password", nullable = false)
    private String password;
    @Column(name="email", nullable = false, unique = true)
    private String email;
    @Column(name = "real_name", nullable = false)
    private String realName;
    @ManyToOne
    @JoinColumn(name = "user_authority")
    private Authority authority;
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

    public UserDTO toDto() {
        return new UserDTO(
                this.userName,
                null,
                this.email,
                this.realName,
                authority.getAuthorityName()
        );
    }
}
