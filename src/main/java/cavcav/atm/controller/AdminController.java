package cavcav.atm.controller;


import cavcav.atm.dto.AdminRegister;
import cavcav.atm.dto.UserResponse;
import cavcav.atm.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody AdminRegister admin) throws Exception {
        return adminService.registerAdmin(admin);
    }

    @GetMapping
    public String as(){
        return " asdasd";
    }

}
