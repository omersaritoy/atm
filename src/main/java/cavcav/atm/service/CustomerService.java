package cavcav.atm.service;


import cavcav.atm.config.AccountNumberGenerator;
import cavcav.atm.dto.CustomerRegistrationRequest;
import cavcav.atm.dto.CustomerResponse;
import cavcav.atm.dto.LoginRequest;
import cavcav.atm.dto.UserResponse;
import cavcav.atm.entity.Admin;
import cavcav.atm.entity.Customer;
import cavcav.atm.entity.Role;
import cavcav.atm.exception.UserAlreadyExistsException;
import cavcav.atm.exception.UserNotFoundException;
import cavcav.atm.repository.AdminRepository;
import cavcav.atm.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public CustomerService(CustomerRepository customerRepository, AdminRepository adminRepository, AccountNumberGenerator accountNumberGenerator, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
        this.accountNumberGenerator = accountNumberGenerator;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ResponseEntity<CustomerResponse> registerCustomer(CustomerRegistrationRequest request)   {
        if(customerRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User Already exist");
        }
        var admin = adminRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new UserNotFoundException("Admin could not be found"));

        Customer customer = new Customer();
        customer.setFirstname(request.getFirstname());
        customer.setLastname(request.getLastname());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setEmail(request.getEmail());
        customer.setRole(Role.CUSTOMER);
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAccountNumber(generateUniqueAccountNumber());
        customer.setAdmin(admin);

        Customer savedCustomer = customerRepository.save(customer);
        System.out.println(" " +customer.getFirstname()+"  " +customer.getLastname()+" " +customer.getPhoneNumber());

        return ResponseEntity.ok(mapToUserResponse(savedCustomer));
    }
    public ResponseEntity<String> login(LoginRequest loginRequest) {
        Customer customer = customerRepository.findByEmail(loginRequest.getEmail());
        if(customer == null) {
            throw new UserNotFoundException("Customer could not be found");
        }
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        if(authentication.isAuthenticated()) {
            String token=jwtService.generateToken(authentication.getName());
            System.out.println("token : Bearer "+token);
            return ResponseEntity.ok("token : Bearer "+token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private CustomerResponse mapToUserResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFirstname(customer.getFirstname());
        response.setLastname(customer.getLastname());
        response.setEmail(customer.getEmail());
        response.setPassword(customer.getPassword());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setRole(customer.getRole());
        return response;
    }
    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = accountNumberGenerator.generateAccountNumber();
        } while (customerRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }
}
