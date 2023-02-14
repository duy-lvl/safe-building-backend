package com.safepass.safebuilding.customer.repository;

import com.safepass.safebuilding.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer findCustomerByPhone(String phone);
}
