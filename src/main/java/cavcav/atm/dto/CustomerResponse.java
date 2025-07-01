package cavcav.atm.dto;

import cavcav.atm.entity.AccountStatus;
import cavcav.atm.entity.Role;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
    private String phoneNumber;
    private String accountNumber;
    private BigDecimal balance;
    private AccountStatus accountStatus;

}
