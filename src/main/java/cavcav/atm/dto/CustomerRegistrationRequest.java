package cavcav.atm.dto;

import lombok.Data;

@Data
public class CustomerRegistrationRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;

}
