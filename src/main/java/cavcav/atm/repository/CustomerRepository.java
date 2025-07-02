package cavcav.atm.repository;

import cavcav.atm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByAccountNumber(String accountNumber);

    @Query("SELECT c FROM Customer c INNER JOIN c.admin a WHERE a.id = :adminId")
    List<Customer> findCustomersByAdminId(@Param("adminId") Long adminId);
}
