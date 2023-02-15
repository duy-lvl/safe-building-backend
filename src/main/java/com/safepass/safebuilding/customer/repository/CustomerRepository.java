package com.safepass.safebuilding.customer.repository;

import com.safepass.safebuilding.admin.entity.Admin;
import com.safepass.safebuilding.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer findCustomerByPhone(String phone);

    Customer findCustomerByEmail(String email);
    @Query("SELECT a FROM Customer a WHERE a.email = ?1 OR a.phone = ?1 AND a.password = ?2")
    Admin loginCustomer(String phoneOrEmail, String password);

    Admin findCustomerByPhoneAndPassword(String phone, String password);
}
