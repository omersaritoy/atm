package cavcav.atm.controller;

import cavcav.atm.dto.CustomerRegistrationRequest;
import cavcav.atm.dto.CustomerResponse;
import cavcav.atm.dto.LoginRequest;
import cavcav.atm.dto.UserResponse;
import cavcav.atm.entity.Customer;
import cavcav.atm.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> register(@RequestBody CustomerRegistrationRequest request) throws Exception {
        return ResponseEntity.ok(customerService.registerCustomer(request).getBody());
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws Exception {
        return customerService.login(loginRequest);
    }
}
