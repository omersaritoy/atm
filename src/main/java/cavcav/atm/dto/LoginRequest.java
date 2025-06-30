package cavcav.atm.dto;


import lombok.Data;

@Data
public class LoginRequest {
    public String email;
    public String password;
}
