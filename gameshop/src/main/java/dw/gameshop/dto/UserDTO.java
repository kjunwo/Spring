package dw.gameshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserDTO {
    private String userName;
    private String password;
    private String email;
    private String realName;
    private String role;
}
