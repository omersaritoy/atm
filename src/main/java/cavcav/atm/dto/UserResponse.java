package cavcav.atm.dto;


import cavcav.atm.entity.Role;
import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Role role;
}
