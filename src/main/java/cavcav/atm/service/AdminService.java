package cavcav.atm.service;

import cavcav.atm.dto.AdminRegister;
import cavcav.atm.dto.LoginRequest;
import cavcav.atm.dto.UserResponse;
import cavcav.atm.entity.Admin;
import cavcav.atm.entity.Role;
import cavcav.atm.exception.UserAlreadyExistsException;
import cavcav.atm.exception.UserNotFoundException;
import cavcav.atm.repository.AdminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {


    public final AdminRepository adminRepository;
    public final BCryptPasswordEncoder bCryptPasswordEncoder;
    public final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ResponseEntity<UserResponse> registerAdmin(AdminRegister request)  {
        if(adminRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User Already Exist");
        }
        Admin admin = new Admin();
        admin.setFirstname(request.getFirstname());
        admin.setLastname(request.getLastname());
        admin.setEmail(request.getEmail());
        admin.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        admin.setRole(Role.ADMIN);
        adminRepository.save(admin);
        return ResponseEntity.ok(mapToUserResponse(admin));
    }
    public ResponseEntity<String> loginAdmin(LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail());
        if(admin == null) {
            throw new UserNotFoundException("Admin Not Found");
        }
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        if(authentication.isAuthenticated()) {
            String token=jwtService.generateToken(authentication.getName());
            System.out.println("token : Bearer "+token);
            return ResponseEntity.ok("token : Bearer "+token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private UserResponse mapToUserResponse(Admin admin) {
        UserResponse response = new UserResponse();
        response.setId(admin.getId());
        response.setFirstname(admin.getFirstname());
        response.setLastname(admin.getLastname());
        response.setEmail(admin.getEmail());
        response.setPassword(admin.getPassword());
        response.setRole(admin.getRole());
        return response;
    }

}
