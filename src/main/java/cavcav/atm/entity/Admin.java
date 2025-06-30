package cavcav.atm.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Admin extends BaseEntity {

    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role=Role.ADMIN;

    @OneToMany(mappedBy = "admin",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Customer> customers;



}
