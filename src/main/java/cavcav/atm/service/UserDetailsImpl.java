package cavcav.atm.service;

import cavcav.atm.entity.Admin;

import cavcav.atm.entity.Customer;
import cavcav.atm.repository.AdminRepository;
import cavcav.atm.repository.CustomerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImpl implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    public UserDetailsImpl(AdminRepository adminRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email);
        if (admin != null) {
            return admin;
        }

        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            return customer;
        }

        throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + email);
    }
}
