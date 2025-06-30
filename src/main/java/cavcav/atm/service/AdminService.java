package cavcav.atm.service;

import cavcav.atm.dto.AdminRegister;
import cavcav.atm.dto.LoginRequest;
import cavcav.atm.dto.UserResponse;
import cavcav.atm.entity.Admin;
import cavcav.atm.entity.Role;
import cavcav.atm.repository.AdminRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {


    public final AdminRepository adminRepository;
    public final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<UserResponse> registerAdmin(AdminRegister request) throws Exception {
        if(adminRepository.existsByEmail(request.getEmail())) {
            throw new Exception("User Already Exist");
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
    public ResponseEntity<?> loginAdmin(LoginRequest request) {


        Admin admin = adminRepository.findByEmail(request.getEmail());
        if(admin == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
        if(!bCryptPasswordEncoder.matches(request.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }
        UserResponse response = mapToUserResponse(admin);
        return ResponseEntity.ok(response);
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
