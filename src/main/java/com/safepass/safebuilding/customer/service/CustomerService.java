package com.safepass.safebuilding.customer.service;

import com.safepass.safebuilding.common.dto.ResponseObject;
import com.safepass.safebuilding.customer.entity.Customer;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    ResponseEntity<ResponseObject> getAllCustomer(int page, int size);
    Optional<Customer> getCustomerById(UUID id);
}
