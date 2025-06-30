package cavcav.atm.service;

import cavcav.atm.dto.AdminRegister;
import cavcav.atm.dto.UserResponse;
import cavcav.atm.entity.Admin;
import cavcav.atm.repository.AdminRepository;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminService {


    public final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public ResponseEntity<UserResponse> registerAdmin(AdminRegister request) throws Exception {
        if(adminRepository.existsByEmail(request.getEmail())) {
            throw new Exception("User Already Exist");
        }
        Admin admin = new Admin();
        admin.setFirstname(request.getFirstname());
        admin.setLastname(request.getLastname());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        adminRepository.save(admin);
        return ResponseEntity.ok(mapToUserResponse(admin));
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
