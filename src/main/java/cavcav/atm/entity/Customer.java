package cavcav.atm.entity;


import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;
    private String accountNumber;
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role=Role.CUSTOMER;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name="admin_id")
    private Admin admin;


}
